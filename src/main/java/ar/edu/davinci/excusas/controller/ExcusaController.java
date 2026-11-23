package ar.edu.davinci.excusas.controller;

import ar.edu.davinci.excusas.dto.EliminacionResponse;
import ar.edu.davinci.excusas.dto.ExcusaRequest;
import ar.edu.davinci.excusas.dto.ExcusaResponse;
import ar.edu.davinci.excusas.service.ExcusaServiceI;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/excusas")
public class ExcusaController implements ExcusaControllerI {

    private final ExcusaServiceI service;

    public ExcusaController(ExcusaServiceI service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<ExcusaResponse> registrar(@Valid @RequestBody ExcusaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @Override
    @GetMapping("/{legajo}")
    public ResponseEntity<List<ExcusaResponse>> porLegajo(@PathVariable int legajo) {
        return ResponseEntity.ok(service.porLegajo(legajo));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ExcusaResponse>> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) String motivo,
            @RequestParam(required = false) String encargado) {
        return ResponseEntity.ok(service.listar(fechaDesde, fechaHasta, motivo, encargado));
    }

    @Override
    @GetMapping("/busqueda")
    public ResponseEntity<List<ExcusaResponse>> buscar(
            @RequestParam int legajo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
        return ResponseEntity.ok(service.buscar(legajo, fechaDesde, fechaHasta));
    }

    @Override
    @GetMapping("/rechazadas")
    public ResponseEntity<List<ExcusaResponse>> rechazadas() {
        return ResponseEntity.ok(service.rechazadas());
    }

    //sin fecha Limite spring responde 400
    @Override
    @DeleteMapping("/eliminar")
    public ResponseEntity<EliminacionResponse> eliminar(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLimite) {
        return ResponseEntity.ok(service.eliminar(fechaLimite));
    }
}
