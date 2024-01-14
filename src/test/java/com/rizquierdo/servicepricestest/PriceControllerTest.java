package com.rizquierdo.servicepricestest;

import com.rizquierdo.servicepricestest.application.usecases.PriceService;
import com.rizquierdo.servicepricestest.domain.model.Brand;
import com.rizquierdo.servicepricestest.domain.model.Price;
import com.rizquierdo.servicepricestest.infraestructure.rest.controllers.PriceController;
import com.rizquierdo.servicepricestest.infraestructure.rest.dto.PriceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

  @Mock
  private PriceService priceService;

  @Mock
  @Qualifier("priceMapper")
  private ModelMapper modelMapper;

  @InjectMocks
  private PriceController priceController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
  }

  @Test
  void testGetPriceInfo() throws Exception {
    Price expectedPrice = Price.builder()
        .productId(35455L)
        .brand(new Brand(1L, "ZARA"))
        .startDate(LocalDateTime.of(2022, 6, 14, 10, 0, 0))
        .endDate(LocalDateTime.of(2022, 12, 31, 23, 59, 59))
        .priceList(1)
        .priority(1)
        .amount(new BigDecimal("35.50"))
        .currency("EUR")
        .build();
    doReturn(expectedPrice).when(priceService).findPriceByBrandIdAndProductIdAndApplicationDate(anyLong(), anyLong(), any());

    // Configuración para ModelMapper
    PriceDto expectedPriceDto = PriceDto.builder().productId(2L)
        .brandId(1L)
        .priceList(1)
        .startDate(LocalDateTime.of(2022, 6, 14, 10, 0, 0))
        .endDate(LocalDateTime.of(2022, 12, 31, 23, 59, 59))
        .amount(new BigDecimal("35.50"))
        .currency("EUR")
        .build();
    doReturn(expectedPriceDto).when(modelMapper).map(expectedPrice, PriceDto.class);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Realiza la solicitud HTTP simulada y verifica la respuesta
    mockMvc.perform(get("/getPriceInfo")
            .param("brandId", "1")
            .param("productId", "35455")
            .param("date", "2022-06-14T10:00:00")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(expectedPriceDto.getProductId()))
        .andExpect(jsonPath("$.brandId").value(expectedPriceDto.getBrandId()))
        .andExpect(jsonPath("$.startDate").value(expectedPriceDto.getStartDate().format(formatter)))
        .andExpect(jsonPath("$.endDate").value(expectedPriceDto.getEndDate().format(formatter)));


  }
}
