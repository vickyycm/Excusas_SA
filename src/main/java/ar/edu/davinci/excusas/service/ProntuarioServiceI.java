package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.ProntuarioResponse;

import java.time.LocalDate;
import java.util.List;

public interface ProntuarioServiceI {
    List<ProntuarioResponse> listar();
    int eliminarDeExcusasAnterioresA(LocalDate fechaLimite);
}
