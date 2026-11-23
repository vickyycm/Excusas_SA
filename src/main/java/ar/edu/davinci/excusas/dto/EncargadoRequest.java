package ar.edu.davinci.excusas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.List;

public record EncargadoRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El email es obligatorio") @Email(message = "El email no es válido") String email,
        @NotNull(message = "El legajo es obligatorio") @Positive(message = "El legajo debe ser positivo") Integer legajo,
        @NotEmpty(message = "Debe indicar al menos un motivo") List<String> motivos,
        String modo
) implements Serializable {
}
