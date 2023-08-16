package com.example.rent_apartment.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "client_application")
@Data
public class ClientApplicationEntity {

    @Id
    @SequenceGenerator(name = "client_applicationSequence", sequenceName = "client_application_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_applicationSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "login_mail")
    private String loginMail;

    @Column(name = "password")
    private String password;

    @Column(name = "commercial_status")
    private boolean commercialStatus;

    @Column(name = "booking_count")
    private Integer bookingCount;

    @Column(name = "parent_city")
    private String parentCity;

}
