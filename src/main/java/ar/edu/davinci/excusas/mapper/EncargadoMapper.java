package ar.edu.davinci.excusas.mapper;

import ar.edu.davinci.excusas.dto.EncargadoRequest;
import ar.edu.davinci.excusas.dto.EncargadoResponse;
import ar.edu.davinci.excusas.model.Delivery;
import ar.edu.davinci.excusas.model.Email;
import ar.edu.davinci.excusas.model.Encargado;
import ar.edu.davinci.excusas.model.EncargadoConfigurable;
import ar.edu.davinci.excusas.model.ModoTrabajo;
import ar.edu.davinci.excusas.model.Motivo;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EncargadoMapper implements EncargadoMapperI {

    //los textos se convierten a enums sino la tira la excepcion
    @Override
    public Encargado aEncargado(EncargadoRequest request, Email email) {
        ModoTrabajo modo = Optional.ofNullable(request.modo()).map(ModoTrabajo::desde).orElse(ModoTrabajo.NORMAL);
        return new EncargadoConfigurable(request.nombre(), request.email(), request.legajo(), email,
                new Delivery(modo.crearModo()),
                request.motivos().stream().map(Motivo::desde).collect(Collectors.toSet()));
    }

    @Override
    public EncargadoResponse aResponse(int posicion, Encargado encargado) {
        return new EncargadoResponse(posicion, encargado.getNumeroLegajo(), encargado.getNombre(),
                encargado.getModoTrabajo().name(),
                encargado.getMotivos().stream().map(Motivo::name).sorted().toList());
    }
}
