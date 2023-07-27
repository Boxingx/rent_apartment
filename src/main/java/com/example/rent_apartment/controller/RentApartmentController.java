package com.example.rent_apartment.controller;

import com.example.rent_apartment.model.dto.GetAddressInfoResponseDto;
import com.example.rent_apartment.model.dto.Location;
import com.example.rent_apartment.service.RentApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.rent_apartment.constant_project.ConstantProject.GET_APARTMENT_BY_PRICE;
import static com.example.rent_apartment.constant_project.ConstantProject.GET_APARTMENT_INFO;

@RestController
public class RentApartmentController {

    @Autowired
    private RentApartmentService rentApartmentService;

//    @GetMapping(GET_APARTMENT_INFO)
//    public List<AddressDto> getAddressInfo(@RequestParam String cityName) {
//        return rentApartmentService.getAddressByCity(cityName);
//    }

    @GetMapping(GET_APARTMENT_BY_PRICE)
    public GetAddressInfoResponseDto getApartmentInfo(@RequestParam String price) {
        Long longPrice = Long.parseLong(price);
        return rentApartmentService.getApartmentByPrice(longPrice);
    }


    /**
     * Данный метод выгружает пользователю квартиры по определенным параметрам,
     */
    @GetMapping(GET_APARTMENT_INFO)
    public GetAddressInfoResponseDto getAddressInfo(@RequestParam String cityName,
                                                    @RequestParam(required = false) String price,
                                                    @RequestParam(required = false) String roomsCount,
                                                    @RequestParam(required = false) String averageRating) {
        if (price == null && roomsCount == null && averageRating == null) {
            return rentApartmentService.getAddressByCity(cityName);
        }
        if (price == null && roomsCount != null && averageRating == null) {
            //TODO   Реализовать метод по городу и кол-ву комнат
            return rentApartmentService.getApartmentByCityAndRoomsCount(cityName, roomsCount);
        }
        if (price != null && roomsCount == null && averageRating == null) {
            return rentApartmentService.getApartmentByCityAndPrice(cityName, price);
            //TODO по городу и цене
        }
        if (price != null && roomsCount != null && averageRating == null) {
            return rentApartmentService.getApartmentByCityAndPriceAndRoomsCount(cityName, price, roomsCount);
            //TODO по всем трем параметрам
        }
        if (price == null && roomsCount == null && averageRating != null) {
            return rentApartmentService.findApartmentEntitiesByAverageRatingAndAddressEntity_City(cityName, averageRating);
        }
        return null;
    }

    /**
     * Описывающий коммент
     * */
    @PostMapping
    public GetAddressInfoResponseDto getApartmentsByLocation(@RequestBody Location location) {
        return null;
    }

    //обычный коммент
    //TODO todo коммент
    /**
     * Описывающий коммент
     * */
}
