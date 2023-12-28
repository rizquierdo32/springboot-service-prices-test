package com.rizquierdo.servicepricestest.infraestructure.rest.dto;

import com.rizquierdo.servicepricestest.domain.model.Brand;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PriceDto   {

  private Long productId;
  private Long brandId;
  private Integer priceList;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private BigDecimal price;
  private String currency;

}
