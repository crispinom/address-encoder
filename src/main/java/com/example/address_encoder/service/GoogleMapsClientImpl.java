package com.example.address_encoder.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.address_encoder.model.GeoCoordinates;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GoogleMapsClientImpl implements AddressEncoderService {

	private static final Logger log = LoggerFactory.getLogger(GoogleMapsClientImpl.class);
	private static final String mapsApi = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";
	private final RestTemplate restTemplate;

	@Value("${googlemaps.token}")
	private String mapsApiKey;

	public GoogleMapsClientImpl(RestTemplateBuilder builder) {

		this.restTemplate = builder.setConnectTimeout(Duration.ofSeconds(5)).build();
	}

	@Override
	public GeoCoordinates encode(String inputAddress) throws IOException {
		String address = null;
		try {
			address = URLEncoder.encode(inputAddress, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			log.error("Error encoding the address", e);
			throw (e);
		}
		log.debug("Encoded address: ", address);
		ResponseEntity<String> response = this.restTemplate.getForEntity(String.format(mapsApi, address, mapsApiKey),
				String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode base;
		try {
			base = mapper.readTree(response.getBody());
		} catch (IOException e) {
			log.error("Error reading response", e);
			throw (e);
		}
		JsonNode location = base.get("results").get(0).get("geometry").get("location");
		return new GeoCoordinates(location.get("lat").asDouble(), location.get("lng").asDouble());
	}

}
