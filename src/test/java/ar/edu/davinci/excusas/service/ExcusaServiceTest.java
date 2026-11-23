package ar.edu.davinci.excusas.service;

import ar.edu.davinci.excusas.dto.EliminacionResponse;
import ar.edu.davinci.excusas.mapper.ExcusaMapper;
import ar.edu.davinci.excusas.repository.ExcusaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExcusaServiceTest {

    private ExcusaRepository repository;
    private ProntuarioServiceI prontuarioService;
    private ExcusaService service;

    @BeforeEach
    void preparar() {
        repository = mock(ExcusaRepository.class);
        prontuarioService = mock(ProntuarioServiceI.class);
        service = new ExcusaService(repository, mock(EmpleadoServiceI.class), mock(EncargadoServiceI.class),
                prontuarioService, new ExcusaMapper());
    }

    @Test
    @DisplayName("T118 Al eliminar se borran primero los prontuarios y despues las excusas")
    void t118_ordenDeEliminacion() {
        LocalDate limite = LocalDate.of(2026, 6, 1);
        when(repository.eliminarAnterioresA(limite)).thenReturn(3);

        EliminacionResponse respuesta = service.eliminar(limite);

        InOrder orden = inOrder(prontuarioService, repository);
        orden.verify(prontuarioService).eliminarDeExcusasAnterioresA(limite);
        orden.verify(repository).eliminarAnterioresA(limite);
        assertEquals(3, respuesta.eliminadas());
    }
}
