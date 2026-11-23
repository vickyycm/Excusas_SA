package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.EliminacionResponse;
import ar.edu.davinci.excusas.dto.ExcusaRequest;
import ar.edu.davinci.excusas.dto.ExcusaResponse;

import java.time.LocalDate;
import java.util.List;

public interface ExcusaServiceI {
    ExcusaResponse registrar(ExcusaRequest request);
    List<ExcusaResponse> porLegajo(int legajo);
    List<ExcusaResponse> listar(LocalDate desde, LocalDate hasta, String motivo, String encargado);
    List<ExcusaResponse> buscar(int legajo, LocalDate desde, LocalDate hasta);
    List<ExcusaResponse> rechazadas();
    EliminacionResponse eliminar(LocalDate fechaLimite);
}
