package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentEntity,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM apartment_info WHERE CAST(price AS bigint) <= :price")
    List<ApartmentEntity> getApartmentInfo(Long price);

}
