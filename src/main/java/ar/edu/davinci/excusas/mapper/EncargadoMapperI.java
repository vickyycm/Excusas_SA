package ar.edu.davinci.excusas.mapper;

import ar.edu.davinci.excusas.dto.EncargadoRequest;
import ar.edu.davinci.excusas.dto.EncargadoResponse;
import ar.edu.davinci.excusas.model.Email;
import ar.edu.davinci.excusas.model.Encargado;

public interface EncargadoMapperI {
    Encargado aEncargado(EncargadoRequest request, Email email);
    EncargadoResponse aResponse(int posicion, Encargado encargado);
}
