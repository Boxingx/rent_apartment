package com.example.rent_apartment.service;

import com.example.rent_apartment.application_exceptions.ApartmentException;
import com.example.rent_apartment.integration.RestTemplateManager;
import com.example.rent_apartment.mapper.ApplicationMapper;
import com.example.rent_apartment.model.dto.*;
import com.example.rent_apartment.model.entity.AddressEntity;
import com.example.rent_apartment.model.entity.ApartmentEntity;
import com.example.rent_apartment.repository.AddressRepository;
import com.example.rent_apartment.repository.ApartmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rent_apartment.config.CityTranslationStatic.getCityInRussianLanguage;
import static com.example.rent_apartment.constant_project.ConstantProject.APARTMENT_STATUS_FALSE;
import static com.example.rent_apartment.constant_project.ConstantProject.APARTMENT_STATUS_TRUE;


@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService {

    private final AddressRepository addressRepository;

    private final ApartmentRepository apartmentRepository;

    private final EntityManager  entityManager;

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
        YandexWeatherResponse weather = getWeatherByLocation(location);
        String infoByLocation = restTemplateManager.getInfoByLocation(location); //ТУТ СОДЕРЖИТСЯ БОЛЬШОЙ JSON В СТРОКЕ, КОТОРЫЙ ПРИШЕЛ НАМ ОТВЕТОМ ОТ ИНТЕГРАЦИОННОГО СЕРВИСА.

        //String englishCity = parseLocationInfo(infoByLocation);

        GetAddressInfoResponseDto addressByCity = getAddressByCity(getCityInRussianLanguage(infoByLocation));

        addressByCity.setTemp(weather.getFactWeather().getTemp());
        addressByCity.setCondition(weather.getFactWeather().getCondition());
        return addressByCity;
    }

    private YandexWeatherResponse getWeatherByLocation(PersonsLocation location) {
        YandexWeatherResponse weatherByLocation = restTemplateManager.getWeatherByLocation(location);
        return weatherByLocation;
    }

    @Override
    public ApartmentWithMessageDto getApartmentById(Long id) {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(id).orElseThrow(() -> new ApartmentException());
        if(apartmentEntity.getStatus().equals("false")) {
            return new ApartmentWithMessageDto(APARTMENT_STATUS_FALSE, applicationMapper.apartmentEntityToApartmentDto(apartmentEntity));
        }
        return new ApartmentWithMessageDto(APARTMENT_STATUS_TRUE, applicationMapper.apartmentEntityToApartmentDto(apartmentEntity));
    }

    //TODO вынести в отдельный метод повторяющееся
    @Override
    public ApartmentWithMessageDto getBookingApartment(Long id, LocalDateTime start, LocalDateTime end) {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(id).orElseThrow(() -> new ApartmentException());
        if(apartmentEntity.getStatus().equals("false")) {
            return new ApartmentWithMessageDto(APARTMENT_STATUS_FALSE, applicationMapper.apartmentEntityToApartmentDto(apartmentEntity));
        }
        apartmentEntity.setStatus("false");
        apartmentRepository.save(apartmentEntity);
        return null;
    }


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
