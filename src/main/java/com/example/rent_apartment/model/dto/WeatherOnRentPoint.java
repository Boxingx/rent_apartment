package com.example.rent_apartment.model.dto;

import lombok.Data;

import static com.example.rent_apartment.constant_project.ConstantProject.WEATHER_NOW;

@Data
public class WeatherOnRentPoint extends RentApartmentException {

    private final String weatherMessage = WEATHER_NOW;

    private String temp;

    private String condition;

}
