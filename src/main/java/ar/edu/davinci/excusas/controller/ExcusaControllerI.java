package ar.edu.davinci.excusas.controller;

import ar.edu.davinci.excusas.dto.EliminacionResponse;
import ar.edu.davinci.excusas.dto.ExcusaRequest;
import ar.edu.davinci.excusas.dto.ExcusaResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface ExcusaControllerI {
    ResponseEntity<ExcusaResponse> registrar(ExcusaRequest request);
    ResponseEntity<List<ExcusaResponse>> porLegajo(int legajo);
    ResponseEntity<List<ExcusaResponse>> listar(LocalDate fechaDesde, LocalDate fechaHasta, String motivo, String encargado);
    ResponseEntity<List<ExcusaResponse>> buscar(int legajo, LocalDate fechaDesde, LocalDate fechaHasta);
    ResponseEntity<List<ExcusaResponse>> rechazadas();
    ResponseEntity<EliminacionResponse> eliminar(LocalDate fechaLimite);
}
