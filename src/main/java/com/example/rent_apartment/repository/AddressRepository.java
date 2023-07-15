package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM address_info WHERE city = :cityName") //NATIVE QUERY
    List<AddressEntity> getAddressInformationByCity(String cityName);

    List<AddressEntity> getAddressEntitiesByCity(String city); //SPRING DATA GENERATION

    @Query(value = "SELECT a FROM AddressEntity a WHERE a.city = :city")
    List<AddressEntity> getAddressInformationByCityByJpql(String city); //JPQL QUERY

    String s = "SELECT * FROM user_info WHERE password = 123 or 1=1";
    String password = "123 or 1=1";

    String gitTesting = "FB_003";
}
