package com.example.rent_apartment.model.dto;

import lombok.Data;

@Data
public class WeatherOnRentPoint extends RentApartmentException {

    private final String weatherMessage = "На момент вашего заезда: ";

    private String temp;

    private String condition;

}
