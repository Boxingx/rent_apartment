package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.ApartmentEntity;
import com.example.rent_apartment.model.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    List<RatingEntity> getRatingEntitiesByApartmentEntity(ApartmentEntity apartmentEntity);

}
