package ar.edu.davinci.excusas.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//cada test corre en una transaccion que se saca al terminar
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext
class ApiIntegracionTest {

    @Autowired
    private MockMvc mvc;

    private ResultActions enviar(org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder pedido,
                                 String json) throws Exception {
        return mvc.perform(pedido.contentType(MediaType.APPLICATION_JSON).content(json));
    }

    private void registrarEmpleado(int legajo) throws Exception {
        enviar(post("/empleados"), "{\"nombre\":\"Juan\",\"email\":\"juan@excusas.com\",\"legajo\":" + legajo + "}")
                .andExpect(status().isCreated());
    }

    private ResultActions registrarExcusa(int legajo, String motivo, String fecha) throws Exception {
        return enviar(post("/excusas"),
                "{\"legajo\":" + legajo + ",\"motivo\":\"" + motivo + "\",\"fecha\":\"" + fecha + "\"}");
    }

    private void cambiarModo(int legajo, String modo) throws Exception {
        enviar(put("/encargados/modo"), "{\"legajo\":" + legajo + ",\"modo\":\"" + modo + "\"}")
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("T95 Se registra un empleado y aparece en el listado")
    void t95_registrarYListarEmpleados() throws Exception {
        registrarEmpleado(1001);

        mvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].legajo").value(1001))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    @DisplayName("T96 Un legajo repetido devuelve 409")
    void t96_legajoDuplicado() throws Exception {
        registrarEmpleado(1001);

        enviar(post("/empleados"), "{\"nombre\":\"Otro\",\"email\":\"otro@excusas.com\",\"legajo\":1001}")
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    @DisplayName("T97 Un empleado con datos invalidos devuelve 400 con los mensajes")
    void t97_empleadoInvalido() throws Exception {
        enviar(post("/empleados"), "{\"nombre\":\"\",\"email\":\"sin-arroba\",\"legajo\":-1}")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(containsString("El nombre es obligatorio")));
    }

    @Test
    @DisplayName("T98 Una excusa trivial la acepta la recepcionista")
    void t98_excusaTrivial() throws Exception {
        registrarEmpleado(1001);

        registrarExcusa(1001, "quedarse_dormido", "2026-11-10")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.motivo").value("QUEDARSE_DORMIDO"))
                .andExpect(jsonPath("$.estado").value("ACEPTADA"))
                .andExpect(jsonPath("$.aceptadaPor").value("Ana"));
    }

    @Test
    @DisplayName("T99 Si la recepcionista esta vaga la excusa trivial termina rechazada")
    void t99_excusaRechazada() throws Exception {
        registrarEmpleado(1001);
        cambiarModo(2001, "VAGO");
        registrarExcusa(1001, "QUEDARSE_DORMIDO", "2026-11-10")
                .andExpect(jsonPath("$.estado").value("RECHAZADA"));
        cambiarModo(2001, "PRODUCTIVO");

        mvc.perform(get("/excusas/rechazadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].estado").value("RECHAZADA"));
    }

    @Test
    @DisplayName("T100 Un motivo inexistente devuelve 400")
    void t100_motivoInvalido() throws Exception {
        registrarEmpleado(1001);

        registrarExcusa(1001, "ME_ABDUJERON", "2026-11-10")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    @DisplayName("T101 Una excusa de un empleado inexistente devuelve 404")
    void t101_empleadoInexistente() throws Exception {
        registrarExcusa(9999, "QUEDARSE_DORMIDO", "2026-11-10")
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("T102 Una excusa inverosimil la acepta el CEO y genera un prontuario")
    void t102_prontuario() throws Exception {
        registrarEmpleado(1001);
        registrarExcusa(1001, "INVEROSIMIL", "2026-11-10")
                .andExpect(jsonPath("$.aceptadaPor").value("Marta"));

        mvc.perform(get("/prontuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].legajo").value(1001))
                .andExpect(jsonPath("$[0].motivo").value("INVEROSIMIL"));
    }

    @Test
    @DisplayName("T103 Se listan las excusas de un legajo")
    void t103_excusasPorLegajo() throws Exception {
        registrarEmpleado(1001);
        registrarExcusa(1001, "QUEDARSE_DORMIDO", "2026-11-10");
        registrarExcusa(1001, "PERDIDA_SUMINISTRO", "2026-11-11");

        mvc.perform(get("/excusas/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("T104 Se filtran las excusas por motivo y encargado")
    void t104_filtrar() throws Exception {
        registrarEmpleado(1001);
        registrarExcusa(1001, "QUEDARSE_DORMIDO", "2026-11-10");
        registrarExcusa(1001, "PERDIDA_SUMINISTRO", "2026-11-11");

        mvc.perform(get("/excusas").param("motivo", "PERDIDA_SUMINISTRO").param("encargado", "Lucia")
                        .param("fechaDesde", "2026-11-01").param("fechaHasta", "2026-11-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].aceptadaPor").value("Lucia"));
    }

    @Test
    @DisplayName("T105 Se buscan las excusas de un legajo entre fechas")
    void t105_busqueda() throws Exception {
        registrarEmpleado(1001);
        registrarExcusa(1001, "QUEDARSE_DORMIDO", "2026-10-01");
        registrarExcusa(1001, "QUEDARSE_DORMIDO", "2026-11-10");

        mvc.perform(get("/excusas/busqueda").param("legajo", "1001")
                        .param("fechaDesde", "2026-11-01").param("fechaHasta", "2026-11-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("T106 Una fecha mal escrita devuelve 400")
    void t106_fechaInvalida() throws Exception {
        mvc.perform(get("/excusas/busqueda").param("legajo", "1001").param("fechaDesde", "ayer"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(containsString("fechaDesde")));
    }

    @Test
    @DisplayName("T107 Se eliminan las excusas anteriores a la fecha limite y se informa cuantas")
    void t107_eliminar() throws Exception {
        registrarEmpleado(1001);
        registrarExcusa(1001, "INVEROSIMIL", "2026-01-10");
        registrarExcusa(1001, "QUEDARSE_DORMIDO", "2026-01-11");
        registrarExcusa(1001, "QUEDARSE_DORMIDO", "2026-11-10");

        mvc.perform(delete("/excusas/eliminar").param("fechaLimite", "2026-06-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eliminadas").value(2));

        mvc.perform(get("/excusas/1001")).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("T108 Sin fecha limite no se elimina nada y se devuelve un error claro")
    void t108_eliminarSinFecha() throws Exception {
        mvc.perform(delete("/excusas/eliminar"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(containsString("fechaLimite")));
    }

    @Test
    @DisplayName("T109 Se listan los encargados en orden con su modo")
    void t109_listarEncargados() throws Exception {
        mvc.perform(get("/encargados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].posicion").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Ana"))
                .andExpect(jsonPath("$[3].nombre").value("Marta"))
                .andExpect(jsonPath("$[0].modo").value("PRODUCTIVO"));
    }

    @Test
    @DisplayName("T110 Un encargado nuevo se suma al final y acepta lo que los demas dejan pasar")
    void t110_nuevoEncargado() throws Exception {
        enviar(post("/encargados"), "{\"nombre\":\"Coach Motivacional\",\"email\":\"coach@excusas.com\","
                + "\"legajo\":3001,\"motivos\":[\"QUEDARSE_DORMIDO\"]}")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.posicion").value(5))
                .andExpect(jsonPath("$.modo").value("NORMAL"));
        registrarEmpleado(1001);
        cambiarModo(2001, "VAGO");

        registrarExcusa(1001, "QUEDARSE_DORMIDO", "2026-11-10")
                .andExpect(jsonPath("$.aceptadaPor").value("Coach Motivacional"));
        cambiarModo(2001, "PRODUCTIVO");
    }

    @Test
    @DisplayName("T111 Un encargado con un motivo inexistente devuelve 400")
    void t111_encargadoMotivoInvalido() throws Exception {
        enviar(post("/encargados"), "{\"nombre\":\"X\",\"email\":\"x@excusas.com\","
                + "\"legajo\":3002,\"motivos\":[\"CUALQUIERA\"]}")
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("T112 Se cambia el modo de un encargado")
    void t112_cambiarModo() throws Exception {
        enviar(put("/encargados/modo"), "{\"legajo\":2002,\"modo\":\"vago\"}")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modo").value("VAGO"))
                .andExpect(jsonPath("$.posicion").value(2));
        cambiarModo(2002, "PRODUCTIVO");
    }

    @Test
    @DisplayName("T113 Un modo inexistente devuelve 400 y un encargado inexistente 404")
    void t113_modoInvalido() throws Exception {
        enviar(put("/encargados/modo"), "{\"legajo\":2002,\"modo\":\"DORMIDO\"}")
                .andExpect(status().isBadRequest());
        enviar(put("/encargados/modo"), "{\"legajo\":7777,\"modo\":\"NORMAL\"}")
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("T114 Un JSON mal formado devuelve 400")
    void t114_jsonInvalido() throws Exception {
        enviar(post("/excusas"), "{esto no es json")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(containsString("formato")));
    }

    @Test
    @DisplayName("T115 Un encargado con legajo repetido devuelve 409")
    void t115_encargadoDuplicado() throws Exception {
        enviar(post("/encargados"), "{\"nombre\":\"Ana2\",\"email\":\"a@excusas.com\","
                + "\"legajo\":2001,\"motivos\":[\"IRRELEVANTE\"]}")
                .andExpect(status().isConflict());
    }
}
