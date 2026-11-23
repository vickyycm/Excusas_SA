package ar.edu.davinci.excusas.controller;

import ar.edu.davinci.excusas.dto.ProntuarioResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProntuarioControllerI {
    ResponseEntity<List<ProntuarioResponse>> listar();
}
