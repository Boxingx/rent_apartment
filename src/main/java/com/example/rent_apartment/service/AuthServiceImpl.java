package com.example.rent_apartment.service;

import com.example.rent_apartment.model.dto.AuthDto;
import com.example.rent_apartment.model.dto.UserSession;
import com.example.rent_apartment.model.entity.ClientApplicationEntity;
import com.example.rent_apartment.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;

    private final UserSession userSession;

    @Override
    public String registration(ClientApplicationEntity clientApplicationEntity) {
        List<ClientApplicationEntity> resultNickName = clientRepository.getClientApplicationEntitiesByNickName(clientApplicationEntity.getNickName());
        if (!resultNickName.isEmpty()) {
            return "Пользователь с таким никнеймом уже существует";
        }
        List<ClientApplicationEntity> resultMail = clientRepository.getClientApplicationEntitiesByLoginMail(clientApplicationEntity.getLoginMail());
        if(!resultMail.isEmpty()) {
            return "Пользователь с таким логином уже существует";
        }
        clientRepository.save(clientApplicationEntity);
        return "Пользователь успешно зарегистрирован";
    }

    @Override
    public String auth(AuthDto authDto) {
        List<ClientApplicationEntity> result = clientRepository.getClientApplicationEntitiesByLoginMail(authDto.getLogin());
        if(result.isEmpty()) {
            return "Пользователя не существует";
        }
        if(result.get(0).getPassword().equals(authDto.getPassword())) {
            userSession.setNickName(result.get(0).getNickName());
            userSession.setLogin(result.get(0).getLoginMail());
            return "Добро пожаловать";
        }
        return "Неправильный пароль";
    }
}
