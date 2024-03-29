package com.rizquierdo.servicepricestest.infraestructure.rest.advice;

import com.rizquierdo.servicepricestest.domain.exception.PriceNotFoundException;
import com.rizquierdo.servicepricestest.infraestructure.rest.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorDto> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorDto.builder().code(HttpStatus.BAD_REQUEST.value())
            .error(ex.getMessage()).build());
  }

  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<ErrorDto>  handlePriceNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorDto.builder().code(HttpStatus.NOT_FOUND.value()).error("Price not found").build());
  }

  // Manejo genérico de otras excepciones
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorDto.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Se ha producido un error interno : "+ ex.getMessage()).build());
  }
}
