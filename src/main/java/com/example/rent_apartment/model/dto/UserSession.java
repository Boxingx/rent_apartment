package com.example.rent_apartment.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserSession {

    private String login;

    private String nickName;

}
