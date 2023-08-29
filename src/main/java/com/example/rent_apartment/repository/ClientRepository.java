package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.ClientApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository <ClientApplicationEntity, Long> {

    List<ClientApplicationEntity> getClientApplicationEntitiesByNickName (String nickName);

    List<ClientApplicationEntity> getClientApplicationEntitiesByLoginMail (String loginMail);

}
