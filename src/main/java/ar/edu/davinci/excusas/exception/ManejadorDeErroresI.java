package ar.edu.davinci.excusas.exception;

import ar.edu.davinci.excusas.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public interface ManejadorDeErroresI {
    ResponseEntity<ErrorResponse> noEncontrado(RuntimeException excepcion);
    ResponseEntity<ErrorResponse> duplicado(LegajoDuplicadoException excepcion);
    ResponseEntity<ErrorResponse> valorInexistente(IllegalArgumentException excepcion);
    ResponseEntity<ErrorResponse> datosInvalidos(MethodArgumentNotValidException excepcion);
    ResponseEntity<ErrorResponse> parametroFaltante(MissingServletRequestParameterException excepcion);
    ResponseEntity<ErrorResponse> formatoInvalido(MethodArgumentTypeMismatchException excepcion);
    ResponseEntity<ErrorResponse> cuerpoInvalido(HttpMessageNotReadableException excepcion);
}
