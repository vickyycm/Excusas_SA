package ar.edu.davinci.excusas.mapper;

import ar.edu.davinci.excusas.dto.ExcusaResponse;
import ar.edu.davinci.excusas.model.ExcusaI;

public interface ExcusaMapperI {
    ExcusaResponse aResponse(ExcusaI excusa);
}
