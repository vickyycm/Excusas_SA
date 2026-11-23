package ar.edu.davinci.excusas.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record ProntuarioResponse(Long id, int legajo, String empleado, Long excusaId, String motivo,
                                 LocalDate fecha) implements Serializable {
}
