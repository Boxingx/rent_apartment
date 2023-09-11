package com.example.rent_apartment.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_info")
@Data
public class ProductEntity {

    @Id
    @SequenceGenerator(name = "product_infoSequence", sequenceName = "product_info_sequence", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_infoSequence")
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "status")
    private String status;


}
