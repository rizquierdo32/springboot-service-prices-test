package com.rizquierdo.servicepricestest.infraestructure.rest.controllers;

import com.rizquierdo.servicepricestest.application.usecases.PriceService;
import com.rizquierdo.servicepricestest.infraestructure.rest.dto.PriceDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Validated
public class PriceController {


  private final PriceService priceService;
  private final ModelMapper modelMapper;

  @Autowired
  public PriceController(PriceService priceService,   @Qualifier("priceMapper") ModelMapper modelMapper) {
    this.priceService = priceService;
    this.modelMapper = modelMapper;
  }


  @GetMapping("/getPriceInfo")
  public ResponseEntity<PriceDto> getPriceInfo(
      @RequestParam Long brandId,
      @RequestParam Long productId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
    return new ResponseEntity<>(modelMapper.map(
        priceService.findPriceByBrandIdAndProductIdAndApplicationDate(brandId,productId,date)
        ,PriceDto.class)
        , HttpStatus.OK);
  }

}
