package com.example.rent_apartment.controller;

import com.example.rent_apartment.model.dto.*;
import com.example.rent_apartment.service.RentApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.example.rent_apartment.constant_project.ConstantProject.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
public class RentApartmentController {

    @Autowired
    private RentApartmentService rentApartmentService;

    @Autowired
    private UserSession userSession;


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
        return new GetAddressInfoResponseDto(new ArrayList<>());
    }

    /**
     * Метод выгружает квартиры по выбранной локации
     */
    @PostMapping(GET_APARTMENT_BY_LOCATION)
    public GetAddressInfoResponseDto getApartmentsByLocation(@Valid @RequestBody PersonsLocation location) {
        return rentApartmentService.getApartmentsByLocation(location);
    }

    /**
     * Метод позволяет просто посмотреть квартиру по id, либо забронировать ее указав id, дату старта и окончания бронирования
     */
    @GetMapping(GET_APARTMENT_BY_ID)
    public ApartmentWithMessageDto getApartmentById(@RequestParam Long id,
                                                    @RequestParam(required = false) LocalDate start,
                                                    @RequestParam(required = false) LocalDate end,
                                                    @RequestParam(required = false) String promoCode) {
        if (isNull(userSession.getNickName())) {
            return new ApartmentWithMessageDto(SIGN_IN, null);
        }

        if (isNull(start) && isNull(end)) {
            return rentApartmentService.getApartmentById(id);
        }
        if (nonNull(start) && nonNull(end)) {
            return rentApartmentService.bookApartment(id, start, end);
        } if (nonNull(start) && nonNull(end) && nonNull(promoCode)) {
            return rentApartmentService.bookApartmentPromoCode(id, start, end, promoCode);
        } else return new ApartmentWithMessageDto();
    }

    /**
     * Метод позволяет добавить новые квартиры.
     */
    @PostMapping(ADD_NEW_APARTMENT)
    public ApartmentWithMessageDto addNewApartment(@RequestBody ApartmentDto apartmentDto) {
        if (isNull(userSession.getNickName())) {
            return new ApartmentWithMessageDto(SIGN_IN, null);
        }
        return rentApartmentService.registrationNewApartment(apartmentDto);
    }




    //31.783272, 34.662766
    //обычный коммент
    //TODO todo коммент
    /**
     * Описывающий коммент
     * */
}
