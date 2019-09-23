
package com.example.address_encoder;


import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.example.address_encoder.model.GeoCoordinates;

@SpringBootTest(classes=AddressEncoderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressEncoderIntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;


	@Test
	@Ignore
	public void testGeoCoordinateServiceUp() throws Exception {
		
		ResponseEntity<GeoCoordinates> response = restTemplate.exchange("http://localhost:" + port + "/encode?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA", HttpMethod.GET,
				new HttpEntity<String>(null, new HttpHeaders()), GeoCoordinates.class);
		
		String expected = "{\"lat\" : 37.4224764, \"lng\" : -122.0842499}";
		assertEquals(expected, response.getBody());

	}
}
