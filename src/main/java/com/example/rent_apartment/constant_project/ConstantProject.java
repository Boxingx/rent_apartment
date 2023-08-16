package com.example.rent_apartment.constant_project;

public class ConstantProject {

    //    PathConstant
    public final static String BASE_PATH = "/api";
    public final static String GET_APARTMENT_INFO = BASE_PATH + "/apartment_info";
    public final static String GET_APARTMENT_BY_PRICE = BASE_PATH + "/apartment_price";
    public final static String GET_APARTMENT_BY_LOCATION = BASE_PATH + "/apartment_location";

    public final static String GET_APARTMENT_BY_ID = BASE_PATH + "/apartments_by_id";

    //REGULAR VALIDATION CONSTANT

    public final static String VALIDATION_LOCATION_PATTERN = "^-?(?:[1-8]?\\d(?:\\.\\d+)?|90(?:\\.0+)?)$";

    //CONSTANT ERROR MESSAGE

    public final static String LATITUDE_ERROR = "Определение вашей локации невозможно";
    public final static String LONGITUDE_ERROR = "Определение вашей локации невозможно";

    //CONSTANT MESSAGE

    public final static String APARTMENT_STATUS_FALSE = "Квартира недоступна для бронирования";

    public final static String APARTMENT_STATUS_TRUE = "Квартира доступна, начать бронирование?";
}
