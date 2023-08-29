package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.BookingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingHistoryRepository extends JpaRepository<BookingHistoryEntity, Long> {
}
