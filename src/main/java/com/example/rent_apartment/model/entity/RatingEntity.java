package com.example.rent_apartment.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rating_apartment_info")
@Data
public class RatingEntity {

    @Id
    @SequenceGenerator(name = "rating_infoSequence", sequenceName = "rating_info_sequence", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "review")
    private String review;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private ApartmentEntity apartmentEntity;
}
