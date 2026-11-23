package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.EncargadoRequest;
import ar.edu.davinci.excusas.dto.EncargadoResponse;
import ar.edu.davinci.excusas.dto.ModoRequest;
import ar.edu.davinci.excusas.exception.EncargadoNoEncontradoException;
import ar.edu.davinci.excusas.exception.LegajoDuplicadoException;
import ar.edu.davinci.excusas.mapper.EncargadoMapperI;
import ar.edu.davinci.excusas.model.CEO;
import ar.edu.davinci.excusas.model.Constructor;
import ar.edu.davinci.excusas.model.Delivery;
import ar.edu.davinci.excusas.model.Email;
import ar.edu.davinci.excusas.model.Encargado;
import ar.edu.davinci.excusas.model.ExcusaI;
import ar.edu.davinci.excusas.model.GerenteRRHH;
import ar.edu.davinci.excusas.model.LineaBuild;
import ar.edu.davinci.excusas.model.LineaDeEncargadosI;
import ar.edu.davinci.excusas.model.ModoTrabajo;
import ar.edu.davinci.excusas.model.Recepcionista;
import ar.edu.davinci.excusas.model.SupervisorArea;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

//administra la linea de encargados en tiempo real
@Service
public class EncargadoService implements EncargadoServiceI {

    private final Email email;
    private final EncargadoMapperI mapper;
    private final List<Encargado> encargados;
    private LineaDeEncargadosI linea;

    public EncargadoService(Email email, EncargadoMapperI mapper) {
        this.email = email;
        this.mapper = mapper;
        this.encargados = new ArrayList<>(List.of(
                new Recepcionista("Ana", "ana@excusas.com", 2001, email, nuevoDelivery()),
                new SupervisorArea("Lucia", "lucia@excusas.com", 2002, email, nuevoDelivery()),
                new GerenteRRHH("Pedro", "pedro@excusas.com", 2003, email, nuevoDelivery()),
                new CEO("Marta", "marta@excusas.com", 2004, email, nuevoDelivery())));
        armarLinea();
    }

    @Override
    public void manejar(ExcusaI excusa) {
        linea.manejar(excusa);
    }

    @Override
    public List<EncargadoResponse> listar() {
        return IntStream.range(0, encargados.size())
                .mapToObj(i -> mapper.aResponse(i + 1, encargados.get(i)))
                .toList();
    }

    //el nuevo encargado se suma al final, antes del rechazador
    @Override
    public EncargadoResponse agregar(EncargadoRequest request) {
        encargados.stream().filter(encargado -> encargado.getNumeroLegajo() == request.legajo())
                .findAny()
                .ifPresent(existente -> {
                    throw new LegajoDuplicadoException(request.legajo());
                });
        Encargado nuevo = mapper.aEncargado(request, email);
        encargados.add(nuevo);
        armarLinea();
        return mapper.aResponse(encargados.size(), nuevo);
    }

    @Override
    public EncargadoResponse cambiarModo(ModoRequest request) {
        Encargado encargado = buscar(request.legajo());
        encargado.cambiarModo(ModoTrabajo.desde(request.modo()).crearModo());
        return mapper.aResponse(encargados.indexOf(encargado) + 1, encargado);
    }

    private Encargado buscar(int legajo) {
        return encargados.stream().filter(encargado -> encargado.getNumeroLegajo() == legajo)
                .findFirst()
                .orElseThrow(() -> new EncargadoNoEncontradoException(legajo));
    }

    private void armarLinea() {
        LineaBuild constructor = new Constructor();
        encargados.forEach(constructor::agregar);
        linea = constructor.construir();
    }

    //a priori arrancan productivos porque no procesaron excusas
    private Delivery nuevoDelivery() {
        return new Delivery(ModoTrabajo.PRODUCTIVO.crearModo());
    }
}
