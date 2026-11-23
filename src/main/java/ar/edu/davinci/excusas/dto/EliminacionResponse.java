package ar.edu.davinci.excusas.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record EliminacionResponse(LocalDate fechaLimite, int eliminadas) implements Serializable {
}
