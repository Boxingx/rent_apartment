package com.example.rent_apartment.service;

import com.example.rent_apartment.model.dto.AuthDto;
import com.example.rent_apartment.model.entity.ClientApplicationEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    String registration(@RequestBody ClientApplicationEntity clientApplicationEntity);

    String auth(@RequestBody AuthDto authDto);
}
