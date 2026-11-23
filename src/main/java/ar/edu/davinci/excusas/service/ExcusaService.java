package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.EliminacionResponse;
import ar.edu.davinci.excusas.dto.ExcusaRequest;
import ar.edu.davinci.excusas.dto.ExcusaResponse;
import ar.edu.davinci.excusas.mapper.ExcusaMapperI;
import ar.edu.davinci.excusas.model.EstadoExcusa;
import ar.edu.davinci.excusas.model.Excusa;
import ar.edu.davinci.excusas.model.Motivo;
import ar.edu.davinci.excusas.repository.ExcusaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExcusaService implements ExcusaServiceI {

    private final ExcusaRepository repository;
    private final EmpleadoServiceI empleadoService;
    private final EncargadoServiceI encargadoService;
    private final ProntuarioServiceI prontuarioService;
    private final ExcusaMapperI mapper;

    public ExcusaService(ExcusaRepository repository, EmpleadoServiceI empleadoService,
                         EncargadoServiceI encargadoService, ProntuarioServiceI prontuarioService,
                         ExcusaMapperI mapper) {
        this.repository = repository;
        this.empleadoService = empleadoService;
        this.encargadoService = encargadoService;
        this.prontuarioService = prontuarioService;
        this.mapper = mapper;
    }

    //se guarda, pasa por la linea de encargados y queda con su estado final
    @Override
    @Transactional
    public ExcusaResponse registrar(ExcusaRequest request) {
        Excusa excusa = repository.save(new Excusa(empleadoService.buscar(request.legajo()),
                Motivo.desde(request.motivo()),
                Optional.ofNullable(request.fecha()).orElseGet(LocalDate::now)));
        encargadoService.manejar(excusa);
        return mapper.aResponse(excusa);
    }

    @Override
    public List<ExcusaResponse> porLegajo(int legajo) {
        empleadoService.buscar(legajo);
        return aResponses(repository.buscarPorLegajo(legajo));
    }

    @Override
    public List<ExcusaResponse> listar(LocalDate desde, LocalDate hasta, String motivo, String encargado) {
        return aResponses(repository.filtrar(desde, hasta,
                Optional.ofNullable(motivo).map(Motivo::desde).orElse(null), encargado));
    }

    @Override
    public List<ExcusaResponse> buscar(int legajo, LocalDate desde, LocalDate hasta) {
        empleadoService.buscar(legajo);
        return aResponses(repository.buscarPorLegajoEntreFechas(legajo, desde, hasta));
    }

    @Override
    public List<ExcusaResponse> rechazadas() {
        return aResponses(repository.findByEstado(EstadoExcusa.RECHAZADA));
    }

    //borra primero los prontuarios de esas excusas para no romper la relacion
    @Override
    @Transactional
    public EliminacionResponse eliminar(LocalDate fechaLimite) {
        prontuarioService.eliminarDeExcusasAnterioresA(fechaLimite);
        return new EliminacionResponse(fechaLimite, repository.eliminarAnterioresA(fechaLimite));
    }

    private List<ExcusaResponse> aResponses(List<Excusa> excusas) {
        return excusas.stream().map(mapper::aResponse).toList();
    }
}
