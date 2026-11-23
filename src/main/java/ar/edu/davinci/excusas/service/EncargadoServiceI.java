package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.EncargadoRequest;
import ar.edu.davinci.excusas.dto.EncargadoResponse;
import ar.edu.davinci.excusas.dto.ModoRequest;
import ar.edu.davinci.excusas.model.ExcusaI;

import java.util.List;

public interface EncargadoServiceI {
    void manejar(ExcusaI excusa);
    List<EncargadoResponse> listar();
    EncargadoResponse agregar(EncargadoRequest request);
    EncargadoResponse cambiarModo(ModoRequest request);
}
