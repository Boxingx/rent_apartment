package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {

//    @Query(nativeQuery = true, value = "SELECT * FROM address_info WHERE city = :cityName") //NATIVE QUERY
//    List<AddressEntity> getAddressInformationByCity(String cityName);

//    List<AddressEntity> getAddressEntitiesByCity(String city); //SPRING DATA GENERATION

//    @Query(value = "SELECT a FROM AddressEntity a WHERE a.city = :city")
//    List<AddressEntity> getAddressInformationByCityByJpql(String city); //JPQL QUERY работает с объектами джава

//    @Query(nativeQuery = true, value = "SELECT address_info.id as address_id, city, street, building_number, apartments_number, apartment_info.id as apt_id, rooms_count, average_rating, price, status, registration_date FROM address_info LEFT JOIN apartment_info ON address_info.apartment_id = apartment_info.id WHERE rooms_count = '4' AND city = 'Москва'")

//    @Query(nativeQuery = true, value = "SELECT apartment_info.id AS apartment_id, apartment_info.rooms_count AS rooms_count, address_info.city AS city FROM apartment_info LEFT JOIN address_info ON apartment_info.id = address_info.apartment_id WHERE apartment_info.rooms_count = 'roomsCount' AND address_info.city = 'cityName'")

//    @Query(nativeQuery = true, value = "SELECT city,street,building_number,apartments_number,apartment_id,rooms_count,average_rating,price,status,registration_date FROM address_info,apartment_info WHERE address_info.city = 'Москва' and CAST(apartment_info.price as bigint) = 5000")
//    List<AddressEntity> getApartmentByCityAndPrice(String cityName, String price);

//    List<AddressEntity> getApartmentByCityAndPriceAndRoomsCount(String cityName, String price, String roomsCount);

    String s = "SELECT * FROM user_info WHERE password = 123 or 1=1";
    String password = "123 or 1=1";

    String gitTesting = "FB_003";

    String gitTest = "Создал переменную в мастере";
    String gitTest2 = "Создал переменную в фб 001";
    String gitTest3 = "Создал переменную в фб 002";

    String gitTest4 = "Создал переменную в фб 004";

}
