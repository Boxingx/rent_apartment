package com.example.rent_apartment.constant_project;

public class ConstantProject {

    //    PathConstant
    public final static String BASE_PATH = "/api";
    public final static String GET_APARTMENT_INFO = BASE_PATH + "/apartment_info";
    public final static String GET_APARTMENT_BY_PRICE = BASE_PATH + "/apartment_price";
    public final static String GET_APARTMENT_BY_LOCATION = BASE_PATH + "/apartment_location";
    public final static String GET_APARTMENT_BY_ID = BASE_PATH + "/apartments_by_id";
    public final static String ADD_NEW_APARTMENT = BASE_PATH + "/new_apartment";
    public final static String REGISTRATION = BASE_PATH + "/registration";
    public final static String AUTH = BASE_PATH + "/auth";


    //REGULAR VALIDATION CONSTANT
    public final static String VALIDATION_LOCATION_PATTERN = "^-?(?:[1-8]?\\d(?:\\.\\d+)?|90(?:\\.0+)?)$";


    //CONSTANT ERROR MESSAGE
    public final static String LATITUDE_ERROR = "Определение вашей локации невозможно";
    public final static String LONGITUDE_ERROR = "Определение вашей локации невозможно";
    public final static String NO_INTEGRATION_DATA = "Отсутствуют базовые параметры для интеграции";


    //CONSTANT CODE
    public final static String ERROR_CODE_250 = "250";
    public final static String ERROR_CODE_255 = "255";
    public final static String ERROR_CODE_260 = "260";
    public final static String ERROR_CODE_265 = "265";

    //CONSTANT MESSAGE
    public final static String SIGN_IN = "Войдите в систему";
    public final static String USER_NOT_EXIST = "Пользователя не существует";
    public final static String WRONG_PASSWORD = "Неправильный пароль";
    public final static String WELCOME = "Добро пожаловать";
    public final static String REGISTRATION_SUCCESSFUL = "Пользователь успешно зарегистрирован";
    public final static String ENTER_APARTMENT_DATA = "Введите данные о квартире";
    public final static String NICKNAME_IS_TAKEN = "Пользователь с таким никнеймом уже существует";
    public final static String LOGIN_IS_TAKEN = "Пользователь с таким логином уже существует";
    public final static String WEATHER_NOW = "На момент вашего заезда: ";
    public final static String CITY_NO_EXISTS = "Такого города не существует";
    public final static String NOT_HAVE_APARTMENT_IN_THIS_CITY = "Нет квартир в этом городе";
    public final static String APARTMENT_SAVED = "Квартира сохранена";
    public final static String NO_APT_WITH_THIS_PRICE = "Нет квартир с ценой ";
    public final static String APARTMENT_STATUS_FALSE = "Квартира недоступна для бронирования";
    public final static String APARTMENT_STATUS_TRUE = "Квартира доступна, начать бронирование?";
    public final static String LOCATION_UNKNOWN = "Неизвестная локация";
    public final static String NO_APARTMENT_WITH_RATING_FILTER = "Апартаментов по условиям фильтра \"рейтинга\" не найдено";
    public final static String NO_APARTMENT_WITH_CITY_FILTER = "Апартаментов по условиям фильтра \"город\" не найдено";
    public final static String NO_APARTMENT_IN_CITY_AND = "Нет квартир в городе ";
    public final static String WITH_PRICE = " c ценой ";
    public final static String AND_ROOMS_COUNT = " и количеством комнат ";
    public final static String ROOMS_COUNT = " с количеством комнат ";

}
