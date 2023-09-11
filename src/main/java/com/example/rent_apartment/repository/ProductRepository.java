package com.example.rent_apartment.repository;


import com.example.rent_apartment.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity getProductEntityByProductName(String name);
}
