package ar.edu.davinci.excusas.controller;

import ar.edu.davinci.excusas.dto.EmpleadoRequest;
import ar.edu.davinci.excusas.dto.EmpleadoResponse;
import ar.edu.davinci.excusas.service.EmpleadoServiceI;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController implements EmpleadoControllerI {

    private final EmpleadoServiceI service;

    public EmpleadoController(EmpleadoServiceI service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Override
    @PostMapping
    public ResponseEntity<EmpleadoResponse> registrar(@Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }
}
