package ar.edu.davinci.excusas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

//la fecha es opcional, si no llega se toma la de hoy
public record ExcusaRequest(
        @NotNull(message = "El legajo es obligatorio") Integer legajo,
        @NotBlank(message = "El motivo es obligatorio") String motivo,
        LocalDate fecha
) implements Serializable {
}
