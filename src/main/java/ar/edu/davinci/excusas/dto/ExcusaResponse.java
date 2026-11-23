package ar.edu.davinci.excusas.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record ExcusaResponse(Long id, int legajo, String empleado, String motivo, LocalDate fecha,
                             String estado, String aceptadaPor, String respuesta) implements Serializable {
}
