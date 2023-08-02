package com.example.rent_apartment.controller;

import com.example.rent_apartment.model.dto.GetAddressInfoResponseDto;
import com.example.rent_apartment.model.dto.PersonsLocation;
import com.example.rent_apartment.service.RentApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.rent_apartment.constant_project.ConstantProject.*;

@RestController
public class RentApartmentController {

    @Autowired
    private RentApartmentService rentApartmentService;


    /**Метод принимает цену и выгружает квартиры по этой цене и ниже*/
    @GetMapping(GET_APARTMENT_BY_PRICE)
    public GetAddressInfoResponseDto getApartmentInfo(@RequestParam String price) {
        Long longPrice = Long.parseLong(price);
        return rentApartmentService.getApartmentByPrice(longPrice);
    }


    /**
     * Метод выгружает пользователю квартиры по определенным параметрам
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
            return rentApartmentService.getApartmentByCityAndRoomsCount(cityName, roomsCount);
        }
        if (price != null && roomsCount == null && averageRating == null) {
            return rentApartmentService.getApartmentByCityAndPrice(cityName, price);
        }
        if (price != null && roomsCount != null && averageRating == null) {
            return rentApartmentService.getApartmentByCityAndPriceAndRoomsCount(cityName, price, roomsCount);
        }
        if (price == null && roomsCount == null && averageRating != null) {
            return rentApartmentService.findApartmentEntitiesByAverageRatingAndAddressEntity_City(cityName, averageRating);
        }
        return null;
    }

    /**
     * Метод выгружает квартиры по выбранной локации
     *
     * */
    @PostMapping(GET_APARTMENT_BY_LOCATION)
    public GetAddressInfoResponseDto getApartmentsByLocation(@Valid @RequestBody PersonsLocation location) {
        return rentApartmentService.getApartmentsByLocation(location);
    }

    //31.783272, 34.662766
    //обычный коммент
    //TODO todo коммент
    /**
     * Описывающий коммент
     * */
}
