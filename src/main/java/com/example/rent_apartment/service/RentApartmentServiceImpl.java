package com.example.rent_apartment.service;

import com.example.rent_apartment.integration.RestTemplateManager;
import com.example.rent_apartment.mapper.ApplicationMapper;
import com.example.rent_apartment.model.dto.AddressDto;
import com.example.rent_apartment.model.dto.ApartmentDto;
import com.example.rent_apartment.model.dto.GetAddressInfoResponseDto;
import com.example.rent_apartment.model.dto.PersonsLocation;
import com.example.rent_apartment.model.entity.AddressEntity;
import com.example.rent_apartment.model.entity.ApartmentEntity;
import com.example.rent_apartment.repository.AddressRepository;
import com.example.rent_apartment.repository.ApartmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rent_apartment.config.CityTranslationStatic.getCityInRussianLanguage;

@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService {

    private final AddressRepository addressRepository;

    private final ApartmentRepository apartmentRepository;

    private final EntityManager entityManager;

    private final ApplicationMapper applicationMapper;

    private final RestTemplateManager restTemplateManager;

    @Override
    public GetAddressInfoResponseDto getAddressByCity(String cityName) {
        List<ApartmentEntity> addressInformation = apartmentRepository.findApartmentEntitiesByAddressEntity_City(cityName);

        if (addressInformation.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode("getAddressByCity");
            getAddressInfoResponseDto.setExceptionMessage("Нет квартир в этом городе");
            return getAddressInfoResponseDto;
        }
        return new GetAddressInfoResponseDto(apartmentEntityToDto(addressInformation));
    }

    //QUERY WITH CRITERIA API
    private List<AddressEntity> getAddressInformationByCriteria(String cityName) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AddressEntity> query = criteriaBuilder.createQuery(AddressEntity.class);
        Root<AddressEntity> root = query.from(AddressEntity.class);

        query.select(root).where(criteriaBuilder.equal(root.get("city"), cityName));
        List<AddressEntity> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }

    public GetAddressInfoResponseDto getApartmentByPrice(Long price) {
        List<ApartmentEntity> apartmentEntityList = apartmentRepository.getApartmentInfo(price);
        if (apartmentEntityList.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode("getApartmentByPrice");
            getAddressInfoResponseDto.setExceptionMessage("Нет квартир с ценой " + price);
            return getAddressInfoResponseDto;
        }
        return new GetAddressInfoResponseDto(apartmentEntityToDto(apartmentEntityList));
    }

    public GetAddressInfoResponseDto getApartmentByCityAndRoomsCount(String city, String roomsCount) {
        List<ApartmentEntity> apartmentEntityList = apartmentRepository.findApartmentEntitiesByRoomsCountAndAddressEntity_City(roomsCount, city);
        if (apartmentEntityList.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode("getApartmentByCityAndRoomsCount");
            getAddressInfoResponseDto.setExceptionMessage("Нет квартир в городе " + city + " c количеством комнат " + roomsCount);
            return getAddressInfoResponseDto;
        }
        return new GetAddressInfoResponseDto(apartmentEntityToDto(apartmentEntityList));
    }

    @Override
    public GetAddressInfoResponseDto getApartmentByCityAndPrice(String city, String price) {
        List<ApartmentEntity> apartmentEntityList = apartmentRepository.findApartmentEntitiesByPriceAndAddressEntity_City(price, city);
        if (apartmentEntityList.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode("getApartmentByCityAndPrice");
            getAddressInfoResponseDto.setExceptionMessage("Нет квартир в городе " + city + " c ценой " + price);
            return getAddressInfoResponseDto;
        }
        return new GetAddressInfoResponseDto(apartmentEntityToDto(apartmentEntityList));
    }

    @Override
    public GetAddressInfoResponseDto getApartmentByCityAndPriceAndRoomsCount(String city, String price, String roomsCount) {
        List<ApartmentEntity> apartmentEntitiesList = apartmentRepository.findApartmentEntitiesByRoomsCountAndPriceAndAddressEntity_City(roomsCount, price, city);
        if (apartmentEntitiesList.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode("getApartmentByCityAndPriceAndRoomsCount");
            getAddressInfoResponseDto.setExceptionMessage("Нет квартир в городе " + city + " c ценой " + price + " и количеством комнат " + roomsCount);
            return getAddressInfoResponseDto;
        }
        return new GetAddressInfoResponseDto(apartmentEntityToDto(apartmentEntitiesList));
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public GetAddressInfoResponseDto findApartmentEntitiesByAverageRatingAndAddressEntity_City(String cityName, String averageRating) {

        GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
        List<ApartmentEntity> apartmentList = apartmentRepository.findApartmentEntitiesByAddressEntity_City(cityName);


        List<ApartmentEntity> apartmentListAfterChange = new ArrayList<>();

        if (apartmentList.isEmpty()) {
            getAddressInfoResponseDto.setExceptionCode("404");
            getAddressInfoResponseDto.setExceptionMessage("Апартаментов по условиям фильтра \"город\" не найдено");
            return getAddressInfoResponseDto;
        }

        for (ApartmentEntity a : apartmentList) {
            Long rating = apartmentRepository.getAvgRatingByCityId(a.getId());
            if (rating != null) {
                a.setAverageRating(apartmentRepository.getAvgRatingByCityId(a.getId()).toString());
                apartmentRepository.save(a);
                apartmentListAfterChange.add(a);
            }
        }

        List<ApartmentEntity> result = apartmentListAfterChange.stream().filter(o -> Integer.parseInt(o.getAverageRating()) <= Integer.parseInt(averageRating)).collect(Collectors.toList());

        if (result.isEmpty()) {
            getAddressInfoResponseDto.setExceptionCode("404");
            getAddressInfoResponseDto.setExceptionMessage("Апартаментов по условиям фильтра \"рейтинга\" не найдено");
            return getAddressInfoResponseDto;
        }
        getAddressInfoResponseDto.setApartmentDtoList(apartmentEntityToDto(result));
        return getAddressInfoResponseDto;
    }

    /**Метод принимает объект с координатами, в случае если объект пустой устанавливается код ошибки и сообщение об ошибке, а если
     * не пустой то получаем JSON из которого вытаскивается нужное поле city(на англ языке) парсится на русский язык с помощью метода getCityInRussianLanguage
     * класса CityTranslationStatic и происходит поиск апартаметов по городу который мы в итоге получили.*/
    @Override
    public GetAddressInfoResponseDto getApartmentsByLocation(PersonsLocation location) {
        GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
        if (location == null) {
            getAddressInfoResponseDto.setExceptionCode("404");
            getAddressInfoResponseDto.setExceptionMessage("Неизвестная локация");
            return getAddressInfoResponseDto;
        }
        String infoByLocation = restTemplateManager.getInfoByLocation(location); //ТУТ СОДЕРЖИТСЯ БОЛЬШОЙ JSON, КОТОРЫЙ ПРИШЕЛ НАМ ОТВЕТОМ ОТ ИНТЕГРАЦИОННОГО СЕРВИСА.

//        String englishCity = parseLocationInfo(infoByLocation);

        return getAddressByCity(getCityInRussianLanguage(englishCity));
    }

    /**Метод принимает больщой JSON и вытаскивает из него нужное нам поле city(на английском языке)*/
//    private String parseLocationInfo(String locationInfo) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            JsonNode jsonNode = objectMapper.readTree(locationInfo);
//            return jsonNode.get("results").get(0).get("components").get("city").asText();
//        } catch (JsonMappingException e) {
//
//        } catch (JsonProcessingException e) {
//
//        }
//        return "Город не найден";
//    }

    private List<ApartmentDto> apartmentEntityToDto(List<ApartmentEntity> apartmentEntityList) {

        List<ApartmentDto> resultList = new ArrayList<>();

        for (ApartmentEntity e : apartmentEntityList) {

            AddressDto addressDto = applicationMapper.addressEntityToAddressDto(e.getAddressEntity());
            ApartmentDto apartmentDto = applicationMapper.apartmentEntityToApartmentDto(e);
            apartmentDto.setAddressDto(addressDto);

            resultList.add(apartmentDto);
        }
        return resultList;
    }

    private List<AddressDto> prepareRequestByAddressInfo(List<AddressEntity> listEntity) {

        List<AddressDto> resultList = new ArrayList<>();

        for (AddressEntity e : listEntity) {

            ApartmentDto apartmentDto = new ApartmentDto();
            apartmentDto.setPrice(e.getApartmentEntity().getPrice());
            apartmentDto.setStatus(e.getApartmentEntity().getStatus());
            apartmentDto.setAverageRating(e.getApartmentEntity().getAverageRating());
            apartmentDto.setRoomsCount(e.getApartmentEntity().getRoomsCount());

            AddressDto addressDto = new AddressDto();
            addressDto.setCity(e.getCity());
            addressDto.setStreet(e.getStreet());
            addressDto.setBuildingNumber(e.getBuildingNumber());
            addressDto.setApartmentsNumber(e.getApartmentsNumber());
            addressDto.setApartmentDto(apartmentDto);
            resultList.add(addressDto);
        }
        return resultList;
    }

}
