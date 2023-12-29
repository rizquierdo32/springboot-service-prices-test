package com.rizquierdo.servicepricestest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rizquierdo.servicepricestest.infraestructure.rest.dto.ErrorDto;
import com.rizquierdo.servicepricestest.infraestructure.rest.dto.PriceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;


	@Test
	public void testIntegration_14_June_10_00() throws Exception {
		assertIntegrationTest(14, 6,10, 0, BigDecimal.valueOf(35.50).setScale(2, RoundingMode.HALF_UP));
	}

	@Test
	public void testIntegration_14_June_16_00() throws Exception {
		assertIntegrationTest(14, 6,16, 0, BigDecimal.valueOf(25.45).setScale(2, RoundingMode.HALF_UP)) ;
	}

	@Test
	public void testIntegration_14_June_21_00() throws Exception {
		assertIntegrationTest(14, 6,21, 0, BigDecimal.valueOf(35.50).setScale(2, RoundingMode.HALF_UP));
	}

	@Test
	public void testIntegration_15_June_10_00() throws Exception {
		assertIntegrationTest(15, 6,10, 0, BigDecimal.valueOf(30.50).setScale(2, RoundingMode.HALF_UP));
	}

	@Test
	public void testIntegration_16_June_21_00() throws Exception {
		assertIntegrationTest(16, 6,21, 0, BigDecimal.valueOf(38.95).setScale(2, RoundingMode.HALF_UP));
	}

	@Test
	public void testIntegrationProductIdNotExist() throws Exception {
		LocalDateTime date = LocalDateTime.of(2020, 2, 2, 2, 2);
		String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/getPriceInfo")
						.param("brandId", "1")
						.param("productId", "35455")
						.param("date", date.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn()
				.getResponse()
				.getContentAsString();
		ErrorDto errorDto = objectMapper.readValue(responseJson, ErrorDto.class);
		assertThat(errorDto.getError()).isEqualTo("Price not found");
		assertThat(errorDto.getCode()).isEqualTo(HttpStatus.NOT_FOUND.value());

	}

	@Test
	public void testIntegrationWithoutDate() throws Exception {
		performBadRequestTest("1", "35455", null,
				"Required request parameter 'date' for method parameter type LocalDateTime is not present",HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testIntegrationWithoutProductId() throws Exception {
		LocalDateTime date = LocalDateTime.of(2020, 2, 2, 2, 2);
		performBadRequestTest("1", null, date.toString(),
				"Required request parameter 'productId' for method parameter type Long is not present",HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testIntegrationWithoutBrandId() throws Exception {
		LocalDateTime date = LocalDateTime.of(2020, 2, 2, 2, 2);
		performBadRequestTest(null, "35455", date.toString(),
				"Required request parameter 'brandId' for method parameter type Long is not present", HttpStatus.BAD_REQUEST.value());
	}


	private void performBadRequestTest(String brandId, String productId, String date, String expectedErrorMessage,int errorCode) throws Exception {
		String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/getPriceInfo")
						.param("brandId", brandId)
						.param("productId", productId)
						.param("date", date)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();

		ErrorDto errorDto = objectMapper.readValue(responseJson, ErrorDto.class);
		assertThat(errorDto.getError()).isEqualTo(expectedErrorMessage);
		assertThat(errorDto.getCode()).isEqualTo(errorCode);
	}


	private void assertIntegrationTest(int day, int month,int hour, int minute, BigDecimal expectedPrice) throws Exception {
		LocalDateTime date = LocalDateTime.of(2020, month, day, hour, minute);
		PriceDto priceDto = performAndGetPriceDto(date);

		// Verifica las propiedades del objeto directamente
		assertThat(priceDto.getBrandId()).isEqualTo(1);
		assertThat(priceDto.getProductId()).isEqualTo(35455);
		assertThat(priceDto.getPrice()).isEqualTo(expectedPrice);
	}

	private PriceDto performAndGetPriceDto(LocalDateTime date) throws Exception {
		String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/getPriceInfo")
						.param("brandId", "1")
						.param("productId", "35455")
						.param("date", date.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		// Convierte la respuesta JSON a un objeto PriceDto
		return objectMapper.readValue(responseJson, PriceDto.class);
	}
}
