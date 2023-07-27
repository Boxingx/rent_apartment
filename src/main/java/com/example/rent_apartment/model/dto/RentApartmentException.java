package com.example.rent_apartment.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentApartmentException {
    private String exceptionCode;
    private String exceptionMessage;
}
