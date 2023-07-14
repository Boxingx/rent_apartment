package com.example.rent_apartment.model.dto;

import lombok.Data;

@Data
public class ApartmentDto {

    private String roomsCount;
    private String averageRating;
    private String price;
    private String status;
    private String registrationDate;
    private AddressDto addressDto;
}
