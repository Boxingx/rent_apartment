package com.example.rent_apartment.model.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "integration_info")
@Data
public class IntegrationInfoEntity {

    @Id
    @SequenceGenerator(name = "integration_infoSequence", sequenceName = "integration_info_sequence", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "integration_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_token")
    private String serviceToken;

    @Column(name = "service_path")
    private String servicePath;
}
