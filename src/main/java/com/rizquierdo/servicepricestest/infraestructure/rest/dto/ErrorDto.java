package com.rizquierdo.servicepricestest.infraestructure.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorDto {

  private String error;
  private int code;

}
