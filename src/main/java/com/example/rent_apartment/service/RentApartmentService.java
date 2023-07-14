package com.example.rent_apartment.service;

import com.example.rent_apartment.model.dto.AddressDto;
import com.example.rent_apartment.model.dto.ApartmentDto;

import java.util.List;

public interface RentApartmentService {
    List<AddressDto> getAddressByCity(String cityName);
    List<ApartmentDto> getApartmentByPrice(Long price);
}
