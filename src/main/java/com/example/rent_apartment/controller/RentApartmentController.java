package com.example.rent_apartment.controller;

import com.example.rent_apartment.model.dto.AddressDto;
import com.example.rent_apartment.model.dto.ApartmentDto;
import com.example.rent_apartment.service.RentApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.rent_apartment.constant_project.ConstantProject.GET_APARTMENT_BY_PRICE;
import static com.example.rent_apartment.constant_project.ConstantProject.GET_APARTMENT_INFO;

@RestController
public class RentApartmentController {

    @Autowired
    private RentApartmentService rentApartmentService;

    @GetMapping(GET_APARTMENT_INFO)
    public List<AddressDto> getAddressInfo(@RequestParam String cityName) {
        return rentApartmentService.getAddressByCity(cityName);
    }

    @GetMapping(GET_APARTMENT_BY_PRICE)
    public List<ApartmentDto> getApartmentInfo(@RequestParam String price) {
        Long longPrice = Long.parseLong(price);
        return rentApartmentService.getApartmentByPrice(longPrice);
    }

}
