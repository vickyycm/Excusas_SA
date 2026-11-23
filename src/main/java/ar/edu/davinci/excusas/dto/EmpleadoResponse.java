package ar.edu.davinci.excusas.dto;

import java.io.Serializable;

public record EmpleadoResponse(int legajo, String nombre, String email) implements Serializable {
}
