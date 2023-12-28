package com.rizquierdo.servicepricestest.infraestructure.rest.advice;

import com.rizquierdo.servicepricestest.infraestructure.rest.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorDto> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
    String paramName = ex.getParameterName();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorDto.builder().code(HttpStatus.BAD_REQUEST.value())
            .error("Error: The parameter '" + paramName + "' is mandatory and has not been reported. : "
                + ex.getMessage()).build());
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorDto>  handleNoSuchElementException(NoSuchElementException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorDto.builder().code(HttpStatus.NOT_FOUND.value()).error(ex.getMessage()).build());
  }
}
