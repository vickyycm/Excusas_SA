package ar.edu.davinci.excusas.mapper;

import ar.edu.davinci.excusas.dto.EmpleadoRequest;
import ar.edu.davinci.excusas.dto.EmpleadoResponse;
import ar.edu.davinci.excusas.model.Empleado;
import ar.edu.davinci.excusas.model.EmpleadoI;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper implements EmpleadoMapperI {

    @Override
    public Empleado aEntidad(EmpleadoRequest request) {
        return new Empleado(request.nombre(), request.email(), request.legajo());
    }

    @Override
    public EmpleadoResponse aResponse(EmpleadoI empleado) {
        return new EmpleadoResponse(empleado.getNumeroLegajo(), empleado.getNombre(), empleado.getEmail());
    }
}
