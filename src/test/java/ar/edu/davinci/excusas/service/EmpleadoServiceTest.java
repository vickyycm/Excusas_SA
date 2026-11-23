package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.EmpleadoRequest;
import ar.edu.davinci.excusas.exception.EmpleadoNoEncontradoException;
import ar.edu.davinci.excusas.exception.LegajoDuplicadoException;
import ar.edu.davinci.excusas.mapper.EmpleadoMapper;
import ar.edu.davinci.excusas.model.Empleado;
import ar.edu.davinci.excusas.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmpleadoServiceTest {

    private EmpleadoRepository repository;
    private EmpleadoService service;

    @BeforeEach
    void preparar() {
        repository = mock(EmpleadoRepository.class);
        service = new EmpleadoService(repository, new EmpleadoMapper());
    }

    @Test
    @DisplayName("T116 Registrar un legajo existente no guarda nada")
    void t116_duplicadoNoGuarda() {
        when(repository.findById(1001)).thenReturn(Optional.of(new Empleado("Juan", "j@e.com", 1001)));

        assertThrows(LegajoDuplicadoException.class,
                () -> service.registrar(new EmpleadoRequest("Juan", "j@e.com", 1001)));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("T117 Buscar un legajo inexistente lanza la excepcion de no encontrado")
    void t117_noEncontrado() {
        when(repository.findById(5)).thenReturn(Optional.empty());

        EmpleadoNoEncontradoException error = assertThrows(EmpleadoNoEncontradoException.class,
                () -> service.buscar(5));
        assertEquals(true, error.getMessage().contains("5"));
    }
}
