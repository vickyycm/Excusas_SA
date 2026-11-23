package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.EmpleadoRequest;
import ar.edu.davinci.excusas.dto.EmpleadoResponse;
import ar.edu.davinci.excusas.model.Empleado;

import java.util.List;

public interface EmpleadoServiceI {
    List<EmpleadoResponse> listar();
    EmpleadoResponse registrar(EmpleadoRequest request);
    Empleado buscar(int legajo);
}
