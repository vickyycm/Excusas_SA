package ar.edu.davinci.excusas.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

//formato unico de error
public record ErrorResponse(int status, String error, String mensaje, LocalDateTime fecha) implements Serializable {
}
