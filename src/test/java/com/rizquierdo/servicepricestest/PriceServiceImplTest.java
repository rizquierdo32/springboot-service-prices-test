package com.rizquierdo.servicepricestest;

import com.rizquierdo.servicepricestest.application.service.PriceServiceImpl;
import com.rizquierdo.servicepricestest.application.usecases.PriceService;
import com.rizquierdo.servicepricestest.domain.exception.PriceNotFoundException;
import com.rizquierdo.servicepricestest.domain.model.Price;
import com.rizquierdo.servicepricestest.infraestructure.persistence.entity.PriceEntity;
import com.rizquierdo.servicepricestest.infraestructure.persistence.repository.JpaPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PriceServiceImplTest {


  @Mock
  private ModelMapper modelMapper;

  @Mock
  private JpaPriceRepository priceRepository;

  private PriceService priceService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    priceService = new PriceServiceImpl(modelMapper, priceRepository);
  }

  @Test
  void testFindPriceByBrandIdAndProductIdAndApplicationDate() {
    PriceEntity priceEntity = new PriceEntity();
    when(priceRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(any(), any(), any(), any()))
        .thenReturn(Optional.of(priceEntity));

    // Configurar el comportamiento del modelMapper mock
    Price expectedPrice = new Price();
    when(modelMapper.map(any(PriceEntity.class), eq(Price.class))).thenReturn(expectedPrice);

    LocalDateTime date = LocalDateTime.now();
    Price result = priceService.findPriceByBrandIdAndProductIdAndApplicationDate(1L, 2L, date);

    assertNotNull(result);
    assertSame(expectedPrice, result);

  }

  @Test
  void testFindPriceByBrandIdAndProductIdAndApplicationDateNotFound() {
    // Configurar el comportamiento del repositorio mock
    when(priceRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(any(), any(), any(), any()))
        .thenReturn(Optional.empty());

    // Llamar al método y verificar que se lanza la excepción esperada
    LocalDateTime date = LocalDateTime.now();
    assertThrows(PriceNotFoundException.class, () -> priceService.findPriceByBrandIdAndProductIdAndApplicationDate(1L, 2L, date));
  }
}
