package ar.edu.davinci.excusas.mapper;

import ar.edu.davinci.excusas.dto.ExcusaResponse;
import ar.edu.davinci.excusas.model.ExcusaI;
import org.springframework.stereotype.Component;

@Component
public class ExcusaMapper implements ExcusaMapperI {

    @Override
    public ExcusaResponse aResponse(ExcusaI excusa) {
        return new ExcusaResponse(excusa.getId(), excusa.getEmpleado().getNumeroLegajo(),
                excusa.getEmpleado().getNombre(), excusa.getMotivo().name(), excusa.getFecha(),
                excusa.getEstado().name(), excusa.getAceptadaPor(), excusa.getRespuesta());
    }
}
