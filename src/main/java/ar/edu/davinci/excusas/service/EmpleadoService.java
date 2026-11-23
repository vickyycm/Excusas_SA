package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.EmpleadoRequest;
import ar.edu.davinci.excusas.dto.EmpleadoResponse;
import ar.edu.davinci.excusas.exception.EmpleadoNoEncontradoException;
import ar.edu.davinci.excusas.exception.LegajoDuplicadoException;
import ar.edu.davinci.excusas.mapper.EmpleadoMapperI;
import ar.edu.davinci.excusas.model.Empleado;
import ar.edu.davinci.excusas.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoService implements EmpleadoServiceI {

    private final EmpleadoRepository repository;
    private final EmpleadoMapperI mapper;

    public EmpleadoService(EmpleadoRepository repository, EmpleadoMapperI mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<EmpleadoResponse> listar() {
        return repository.findAll().stream().map(mapper::aResponse).toList();
    }

    @Override
    @Transactional
    public EmpleadoResponse registrar(EmpleadoRequest request) {
        repository.findById(request.legajo()).ifPresent(existente -> {
            throw new LegajoDuplicadoException(request.legajo());
        });
        return mapper.aResponse(repository.save(mapper.aEntidad(request)));
    }

    @Override
    public Empleado buscar(int legajo) {
        return repository.findById(legajo).orElseThrow(() -> new EmpleadoNoEncontradoException(legajo));
    }
}
