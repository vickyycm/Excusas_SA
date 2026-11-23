package ar.edu.davinci.excusas.controller;

import ar.edu.davinci.excusas.dto.EmpleadoRequest;
import ar.edu.davinci.excusas.dto.EmpleadoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmpleadoControllerI {
    ResponseEntity<List<EmpleadoResponse>> listar();
    ResponseEntity<EmpleadoResponse> registrar(EmpleadoRequest request);
}
