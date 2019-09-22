package com.example.address_encoder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.address_encoder.model.GeoCoordinates;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressEncoderApplicationTests {

	
	@Test
	public void testGeoCoordinatesOutput() throws Exception {
		
		GeoCoordinates gcTest = new GeoCoordinates(0, 0);
		assertEquals(0, gcTest.getLatitude(), 0);
		assertEquals(0, gcTest.getLongtitude(),0);
	}
}
