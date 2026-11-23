package ar.edu.davinci.excusas.dto;

import java.io.Serializable;
import java.util.List;

public record EncargadoResponse(int posicion, int legajo, String nombre, String modo,
                                List<String> motivos) implements Serializable {
}
