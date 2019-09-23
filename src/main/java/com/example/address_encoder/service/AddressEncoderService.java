package com.example.address_encoder.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.address_encoder.model.GeoCoordinates;


/**
 * Service interface to expose methods for address encoding
 * 
 * @author cris
 *
 */
@Service
public interface AddressEncoderService {

	/**
	 * @param inputAddress
	 * @return GeoCoordinates
	 * @throws IOException
	 */
	GeoCoordinates encode(String inputAddress) throws IOException;

}
