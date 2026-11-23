package ar.edu.davinci.excusas.exception;

import ar.edu.davinci.excusas.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

//traduce cada excepcion a un codigo http
@RestControllerAdvice
public class ManejadorDeErrores implements ManejadorDeErroresI {

    @Override
    @ExceptionHandler({EmpleadoNoEncontradoException.class, EncargadoNoEncontradoException.class})
    public ResponseEntity<ErrorResponse> noEncontrado(RuntimeException excepcion) {
        return respuesta(HttpStatus.NOT_FOUND, excepcion.getMessage());
    }

    @Override
    @ExceptionHandler(LegajoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> duplicado(LegajoDuplicadoException excepcion) {
        return respuesta(HttpStatus.CONFLICT, excepcion.getMessage());
    }

    @Override
    @ExceptionHandler({MotivoInexistenteException.class, ModoInexistenteException.class})
    public ResponseEntity<ErrorResponse> valorInexistente(IllegalArgumentException excepcion) {
        return respuesta(HttpStatus.BAD_REQUEST, excepcion.getMessage());
    }

    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> datosInvalidos(MethodArgumentNotValidException excepcion) {
        return respuesta(HttpStatus.BAD_REQUEST, excepcion.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(", ")));
    }

    @Override
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> parametroFaltante(MissingServletRequestParameterException excepcion) {
        return respuesta(HttpStatus.BAD_REQUEST, "Falta el parámetro obligatorio " + excepcion.getParameterName()
                + ", la operación no se realizó");
    }

    @Override
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> formatoInvalido(MethodArgumentTypeMismatchException excepcion) {
        return respuesta(HttpStatus.BAD_REQUEST, "El parámetro " + excepcion.getName() + " tiene un formato inválido");
    }

    @Override
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> cuerpoInvalido(HttpMessageNotReadableException excepcion) {
        return respuesta(HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud no tiene un formato válido");
    }

    private ResponseEntity<ErrorResponse> respuesta(HttpStatus estado, String mensaje) {
        return ResponseEntity.status(estado)
                .body(new ErrorResponse(estado.value(), estado.getReasonPhrase(), mensaje, LocalDateTime.now()));
    }
}
