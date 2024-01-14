package com.rizquierdo.servicepricestest.infraestructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "BRAND")
public class BrandEntity {

	@Id
	@Column(name = "ID")
	private Long id;
	@Column(name = "NAME")
	private String name;
	
}