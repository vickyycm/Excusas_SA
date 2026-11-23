package ar.edu.davinci.excusas.mapper;

import ar.edu.davinci.excusas.dto.ProntuarioResponse;
import ar.edu.davinci.excusas.model.ProntuarioI;

public interface ProntuarioMapperI {
    ProntuarioResponse aResponse(ProntuarioI prontuario);
}
