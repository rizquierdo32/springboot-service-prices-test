package com.rizquierdo.servicepricestest.infraestructure.mapper;

import com.rizquierdo.servicepricestest.domain.model.Brand;
import com.rizquierdo.servicepricestest.domain.model.Price;
import com.rizquierdo.servicepricestest.infraestructure.persistence.entity.PriceEntity;
import com.rizquierdo.servicepricestest.infraestructure.rest.dto.PriceDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper priceMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
    modelMapper.typeMap(PriceEntity.class, Price.class).addMappings(mapper -> {
      mapper.map(PriceEntity::getBrand, Price::setBrand);
    });
    modelMapper.typeMap(Price.class, PriceDto.class);
    return modelMapper;
  }
}
