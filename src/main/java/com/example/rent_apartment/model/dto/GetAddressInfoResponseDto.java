package com.example.rent_apartment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetAddressInfoResponseDto extends RentApartmentException {

    private List<ApartmentDto> apartmentDtoList;

}
