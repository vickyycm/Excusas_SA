package ar.edu.davinci.excusas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ModoRequest(
        @NotNull(message = "El legajo es obligatorio") Integer legajo,
        @NotBlank(message = "El modo es obligatorio") String modo
) implements Serializable {
}
