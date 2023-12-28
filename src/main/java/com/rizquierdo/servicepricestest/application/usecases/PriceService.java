package com.rizquierdo.servicepricestest.application.usecases;

import com.rizquierdo.servicepricestest.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceService {
  Price findPriceByBrandIdAndProductIdAndApplicationDate(Long brandId, Long productId, LocalDateTime applicationDate);
}
