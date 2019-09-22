package com.example.address_encoder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.address_encoder.controller.AddressEncoderController;
import com.example.address_encoder.model.GeoCoordinates;
import com.example.address_encoder.service.AddressEncoderService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AddressEncoderController.class)
public class AddressEncoderControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AddressEncoderService addressEncoderService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getEncodedGeoCoordinates() throws Exception {
		GeoCoordinates mockGeoCoordinates = new GeoCoordinates(1.23, 2.13);
		String expectedResult = "{\"latitude\":1.23,\"longtitude\":2.13}";
		Mockito.when(addressEncoderService.encode(Mockito.anyString())).thenReturn(mockGeoCoordinates);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/encode?address=1234")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals(expectedResult, result.getResponse().getContentAsString());

	}

	@Test
	public void checkRateLimiter() throws Exception {
		GeoCoordinates mockGeoCoordinates = new GeoCoordinates(4.56, 5.64);

		Mockito.when(addressEncoderService.encode(Mockito.anyString())).thenReturn(mockGeoCoordinates);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/encode?address=1234")
				.accept(MediaType.APPLICATION_JSON);

		for (int i = 0; i < 10; i++) {
			mockMvc.perform(requestBuilder).andReturn();
		}
		Mockito.verify(addressEncoderService, Mockito.times(4)).encode(Mockito.anyString());

	}


}
