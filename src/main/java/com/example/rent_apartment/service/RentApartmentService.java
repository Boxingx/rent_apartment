package com.example.rent_apartment.service;

import com.example.rent_apartment.model.dto.ApartmentDto;
import com.example.rent_apartment.model.dto.ApartmentWithMessageDto;
import com.example.rent_apartment.model.dto.GetAddressInfoResponseDto;
import com.example.rent_apartment.model.dto.PersonsLocation;

import java.time.LocalDateTime;

public interface RentApartmentService {

    GetAddressInfoResponseDto getAddressByCity(String cityName);

    GetAddressInfoResponseDto getApartmentByPrice(Long price);

    GetAddressInfoResponseDto getApartmentByCityAndRoomsCount(String city, String roomsCount);

    GetAddressInfoResponseDto getApartmentByCityAndPrice(String city, String price);

    GetAddressInfoResponseDto getApartmentByCityAndPriceAndRoomsCount(String city, String price, String roomsCount);

    GetAddressInfoResponseDto findApartmentEntitiesByAverageRatingAndAddressEntity_City(String cityName, String averageRating);

    GetAddressInfoResponseDto getApartmentsByLocation(PersonsLocation location);

    ApartmentWithMessageDto getApartmentById(Long id);

    ApartmentWithMessageDto getBookingApartment(Long id, LocalDateTime start, LocalDateTime end);

    ApartmentWithMessageDto registrationNewApartment(ApartmentDto apartmentDto);

}

