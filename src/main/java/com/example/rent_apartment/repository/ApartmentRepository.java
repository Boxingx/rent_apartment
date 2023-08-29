package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM apartment_info WHERE CAST(price AS bigint) <= :price")
    List<ApartmentEntity> getApartmentInfo(Long price);

    List<ApartmentEntity> findApartmentEntitiesByAddressEntity_City(String cityName);

    List<ApartmentEntity> findApartmentEntitiesByPriceAndAddressEntity_City(String price, String cityName);

    List<ApartmentEntity> findApartmentEntitiesByRoomsCountAndAddressEntity_City(String roomsCount, String cityName);

    List<ApartmentEntity> findApartmentEntitiesByRoomsCountAndPriceAndAddressEntity_City(String roomsCount, String price, String cityName);

    List<ApartmentEntity> findApartmentEntitiesByAverageRatingAndAddressEntity_City(String averageRating, String cityName);

    @Query(nativeQuery = true, value = "SELECT id from address_info where city = :city")
    Long[] getApartmentsIdByCity(String city);

    @Query(nativeQuery = true, value = "SELECT AVG(rating) from rating_apartment_info where apartment_id = :apartmentId")
    Long getAvgRatingByCityId(Long apartmentId);

    @Query(nativeQuery = true, value = "UPDATE apartment_info SET average_rating = :averageRating where id = :apartmentId")
    void setNewAvgRatingForApartmentById(@Param("averageRating") String averageRating, @Param("apartmentId") Long apartmentId);

    @Query(nativeQuery = true, value = "SELECT * FROM apartment_info where id = :apartmentId and average_rating = :averageRating")
    ApartmentEntity getApartmentEntitiesByIdAndAverageRating(Long apartmentId, String averageRating);

    ApartmentEntity getApartmentEntityById(Long id);
}
