package com.example.address_encoder.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.address_encoder.model.GeoCoordinates;
import com.example.address_encoder.service.AddressEncoderService;


/**
 * 
 * Address Encoder controller class that handles encoding/decoding of full addresses to
 * geo coordinates using an address encoding service object. 
 * Also maintains the rate limiting per address using a Map object. 
 * 
 * @author cris
 *
 */
@RestController
public class AddressEncoderController {

	private static final Logger log = LoggerFactory.getLogger(AddressEncoderController.class);
	private final AddressEncoderService addressEncoderService;
	private static Map<String, Integer> addressMap = new HashMap<>();

	@Value("${attempt.limit}")
	private String maxAttempts;

	public AddressEncoderController(AddressEncoderService addressEncoderService) {
		this.addressEncoderService = addressEncoderService;
	}

	/**
	 * 
	 * Controller method to encode the address given as input.
	 * Returns a json object with GeoCoordinates or a message indicating an error 
	 * or resource limits reached. 
	 * Enforces the rate limiting by address.
	 * 
	 * @param inputAddress
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/encode")
	public ResponseEntity<?> encode(@RequestParam(value = "address") String inputAddress) throws IOException {
		addressMap.merge(inputAddress, 0, (v1, v2) -> v1 + 1);

		log.debug("Input address received:", inputAddress);
		if (addressMap.get(inputAddress) < Integer.valueOf(maxAttempts)) {
			GeoCoordinates geoCoordinates = addressEncoderService.encode(inputAddress);
			return new ResponseEntity<GeoCoordinates>(geoCoordinates, HttpStatus.OK);

		} else {
			return ResponseEntity.of(
					Optional.of(String.format("{\"message\":\"Resource Limit Exceeded %s attempts\"}", maxAttempts)));
		}
	}

}
