package ar.edu.davinci.excusas.controller;

import ar.edu.davinci.excusas.dto.EncargadoRequest;
import ar.edu.davinci.excusas.dto.EncargadoResponse;
import ar.edu.davinci.excusas.dto.ModoRequest;
import ar.edu.davinci.excusas.service.EncargadoServiceI;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/encargados")
public class EncargadoController implements EncargadoControllerI {

    private final EncargadoServiceI service;

    public EncargadoController(EncargadoServiceI service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EncargadoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Override
    @PostMapping
    public ResponseEntity<EncargadoResponse> agregar(@Valid @RequestBody EncargadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.agregar(request));
    }

    @Override
    @PutMapping("/modo")
    public ResponseEntity<EncargadoResponse> cambiarModo(@Valid @RequestBody ModoRequest request) {
        return ResponseEntity.ok(service.cambiarModo(request));
    }
}
