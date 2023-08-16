package com.example.rent_apartment.integration;

import com.example.rent_apartment.model.dto.Components;
import com.example.rent_apartment.model.dto.GeoCoderResponse;
import com.example.rent_apartment.model.dto.PersonsLocation;
import com.example.rent_apartment.model.dto.ResultIndexElement;
import com.example.rent_apartment.model.entity.IntegrationInfoEntity;
import com.example.rent_apartment.repository.IntegrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestTemplateManager {

    private final IntegrationRepository integrationRepository;

    public String getInfoByLocation(PersonsLocation location) {

        RestTemplate restTemplate = new RestTemplate();
        IntegrationInfoEntity config = integrationRepository.findById(1l)
                .orElseThrow(() -> new RuntimeException("Отсутствуют базовые параметры для интеграции"));

        GeoCoderResponse locationInfo = restTemplate.exchange(String.format(config.getServicePath(),
                        location.getLatitude(),
                        location.getLongitude(),
                        config.getServiceToken()),
                HttpMethod.GET,
                new HttpEntity<>(null),
                GeoCoderResponse.class).getBody();
        return parseInfoByLocation(locationInfo);
    }


    private String parseInfoByLocation(GeoCoderResponse locationInfo) {
        List<ResultIndexElement> resultsObject = locationInfo.getResultsObject();
        ResultIndexElement resultIndexElement = resultsObject.get(0);
        Components components = resultIndexElement.getComponents();
        if (components.getCity() != null) {
            return components.getCity();
        } else if (components.getTown() != null) {
            return components.getTown();
        }
        return "Такого города не существует";
    }
}
