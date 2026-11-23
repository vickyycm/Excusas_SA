package ar.edu.davinci.excusas.mapper;

import ar.edu.davinci.excusas.dto.ProntuarioResponse;
import ar.edu.davinci.excusas.model.ProntuarioI;
import org.springframework.stereotype.Component;

@Component
public class ProntuarioMapper implements ProntuarioMapperI {

    @Override
    public ProntuarioResponse aResponse(ProntuarioI prontuario) {
        return new ProntuarioResponse(prontuario.getId(), prontuario.getNroLegajo(),
                prontuario.getEmpleado().getNombre(), prontuario.getExcusa().getId(),
                prontuario.getExcusa().getMotivo().name(), prontuario.getExcusa().getFecha());
    }
}
