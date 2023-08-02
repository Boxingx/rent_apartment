package com.example.rent_apartment.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

import static com.example.rent_apartment.constant_project.ConstantProject.*;

@Getter
@Setter
public class PersonsLocation {

    @Pattern(regexp = "^\\d+$", message = LATITUDE_ERROR)
    private String latitude;

    @Pattern(regexp = VALIDATION_LOCATION_PATTERN, message = LONGITUDE_ERROR)
    private String longitude;

}
