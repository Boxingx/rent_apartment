package com.example.rent_apartment.model.dto.geocoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
@Getter
@Setter
@ToString
public class Components {

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "town")
    private String town;


}
