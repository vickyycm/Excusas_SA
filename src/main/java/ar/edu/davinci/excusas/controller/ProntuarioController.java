package ar.edu.davinci.excusas.controller;

import ar.edu.davinci.excusas.dto.ProntuarioResponse;
import ar.edu.davinci.excusas.service.ProntuarioServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController implements ProntuarioControllerI {

    private final ProntuarioServiceI service;

    public ProntuarioController(ProntuarioServiceI service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProntuarioResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
