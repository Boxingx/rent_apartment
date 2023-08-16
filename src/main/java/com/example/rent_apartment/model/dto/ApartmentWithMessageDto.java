package com.example.rent_apartment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentWithMessageDto {

    private String message;

    private ApartmentDto apartmentDto;

}
