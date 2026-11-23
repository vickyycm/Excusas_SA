package ar.edu.davinci.excusas.controller;

import ar.edu.davinci.excusas.dto.EncargadoRequest;
import ar.edu.davinci.excusas.dto.EncargadoResponse;
import ar.edu.davinci.excusas.dto.ModoRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EncargadoControllerI {
    ResponseEntity<List<EncargadoResponse>> listar();
    ResponseEntity<EncargadoResponse> agregar(EncargadoRequest request);
    ResponseEntity<EncargadoResponse> cambiarModo(ModoRequest request);
}
