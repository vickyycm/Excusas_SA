package ar.edu.davinci.excusas.mapper;

import ar.edu.davinci.excusas.dto.EmpleadoRequest;
import ar.edu.davinci.excusas.dto.EmpleadoResponse;
import ar.edu.davinci.excusas.model.Empleado;
import ar.edu.davinci.excusas.model.EmpleadoI;

public interface EmpleadoMapperI {
    Empleado aEntidad(EmpleadoRequest request);
    EmpleadoResponse aResponse(EmpleadoI empleado);
}
