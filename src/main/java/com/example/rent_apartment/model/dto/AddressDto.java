package com.example.rent_apartment.model.dto;

import lombok.Data;

@Data
public class AddressDto {

    private String city;
    private String street;
    private String buildingNumber;
    private String apartmentsNumber;
    private ApartmentDto apartmentDto;

}
