package com.example.rent_apartment.config;

import java.util.HashMap;
import java.util.Map;

import static com.example.rent_apartment.constant_project.ConstantProject.NOT_HAVE_APARTMENT_IN_THIS_CITY;

/**Класс со статической мапой, статическим блоком который инициализирует мапу и со статическим методом который
 * принимает город на английском языке(как ключ) и отдает значение по заданному ключу в виде города, но уже на русском языке
 * */
public class CityTranslationStatic {

    private static final Map<String, String> cityTranslationMap = new HashMap<>();

    static {
        cityTranslationMap.put("Moscow", "Москва");
        cityTranslationMap.put("Saint Petersburg", "Санкт-Петербург");
        cityTranslationMap.put("Omsk", "Омск");
        cityTranslationMap.put("Tyumen", "Тюмень");
        cityTranslationMap.put("Novosibirsk", "Новосибирск");
        cityTranslationMap.put("Krasnoyarsk", "Красноярск");
        cityTranslationMap.put("Krasnodar", "Краснодар");
        cityTranslationMap.put("Makhachkala", "Махачкала");
        cityTranslationMap.put("Penza", "Пенза");
        cityTranslationMap.put("Tver", "Тверь");
        cityTranslationMap.put("Ashdod", "Ashdod");
    }

    public static String getCityInRussianLanguage(String englishCity) {
        return cityTranslationMap.getOrDefault(englishCity,NOT_HAVE_APARTMENT_IN_THIS_CITY);
    }
}
