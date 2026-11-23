package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.ProntuarioResponse;
import ar.edu.davinci.excusas.mapper.ProntuarioMapperI;
import ar.edu.davinci.excusas.model.AdministradorProntuario;
import ar.edu.davinci.excusas.model.Observador;
import ar.edu.davinci.excusas.model.Prontuario;
import ar.edu.davinci.excusas.model.ProntuarioI;
import ar.edu.davinci.excusas.repository.ProntuarioRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

//OBSERVER: se suscribe al administrador y guarda en la base cada prontuario nuevo
@Service
public class ProntuarioService implements ProntuarioServiceI, Observador {

    private final ProntuarioRepository repository;
    private final ProntuarioMapperI mapper;

    public ProntuarioService(ProntuarioRepository repository, ProntuarioMapperI mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void suscribirse() {
        AdministradorProntuario.getInstancia().agregarObservador(this);
    }

    @PreDestroy
    public void desuscribirse() {
        AdministradorProntuario.getInstancia().eliminarObservador(this);
    }

    @Override
    public void actualizar(ProntuarioI prontuario) {
        repository.save(new Prontuario(prontuario.getEmpleado(), prontuario.getExcusa()));
    }

    @Override
    public List<ProntuarioResponse> listar() {
        return repository.findAll().stream().map(mapper::aResponse).toList();
    }

    @Override
    @Transactional
    public int eliminarDeExcusasAnterioresA(LocalDate fechaLimite) {
        return repository.eliminarDeExcusasAnterioresA(fechaLimite);
    }
}
