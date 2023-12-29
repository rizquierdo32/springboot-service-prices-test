package com.rizquierdo.servicepricestest.application.service;

import com.rizquierdo.servicepricestest.domain.model.Price;
import com.rizquierdo.servicepricestest.application.usecases.PriceService;
import com.rizquierdo.servicepricestest.infraestructure.persistence.repository.JpaPriceRepository;
import com.rizquierdo.servicepricestest.infraestructure.persistence.entity.PriceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

  @Qualifier("priceMapper")
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private JpaPriceRepository priceRepository;

  public Price findPriceByBrandIdAndProductIdAndApplicationDate(Long brandId, Long productId, LocalDateTime date) {
    Optional<PriceEntity> optPrice = priceRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(productId, brandId, date, date);
    return optPrice.map(x -> modelMapper.map(x, Price.class))
        .orElseThrow(NoSuchElementException::new);
  }
}
