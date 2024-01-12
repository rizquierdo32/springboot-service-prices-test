package com.rizquierdo.servicepricestest.infraestructure.persistence.repository;

import com.rizquierdo.servicepricestest.infraestructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

  Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
      Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate);
}
