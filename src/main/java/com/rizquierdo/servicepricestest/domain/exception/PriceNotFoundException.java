package com.rizquierdo.servicepricestest.domain.exception;

public class PriceNotFoundException extends RuntimeException {

  public PriceNotFoundException(String message) {
    super(message);
  }

  public PriceNotFoundException() {
  }
}
