package com.example.address_encoder.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.address_encoder.model.GeoCoordinates;

@Service
public interface AddressEncoderService {

	GeoCoordinates encode(String inputAddress) throws IOException;

}
