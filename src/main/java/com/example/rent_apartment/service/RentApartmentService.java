package com.example.rent_apartment.service;

import com.example.rent_apartment.model.dto.GetAddressInfoResponseDto;

public interface RentApartmentService {
    GetAddressInfoResponseDto getAddressByCity(String cityName);
    GetAddressInfoResponseDto getApartmentByPrice(Long price);

    GetAddressInfoResponseDto getApartmentByCityAndRoomsCount(String city, String roomsCount);

    GetAddressInfoResponseDto getApartmentByCityAndPrice(String city, String price);

    GetAddressInfoResponseDto getApartmentByCityAndPriceAndRoomsCount(String city, String price, String roomsCount);

    GetAddressInfoResponseDto findApartmentEntitiesByAverageRatingAndAddressEntity_City(String cityName, String averageRating);
}
