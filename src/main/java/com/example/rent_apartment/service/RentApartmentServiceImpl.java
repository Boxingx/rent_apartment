package com.example.rent_apartment.service;

import com.example.rent_apartment.application_exceptions.ApartmentException;
import com.example.rent_apartment.integration.RestTemplateManager;
import com.example.rent_apartment.mapper.ApplicationMapper;
import com.example.rent_apartment.model.dto.*;
import com.example.rent_apartment.model.dto.yandex_integration.YandexWeatherResponse;
import com.example.rent_apartment.model.entity.AddressEntity;
import com.example.rent_apartment.model.entity.ApartmentEntity;
import com.example.rent_apartment.model.entity.BookingHistoryEntity;
import com.example.rent_apartment.model.entity.ClientApplicationEntity;
import com.example.rent_apartment.repository.AddressRepository;
import com.example.rent_apartment.repository.ApartmentRepository;
import com.example.rent_apartment.repository.BookingHistoryRepository;
import com.example.rent_apartment.repository.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rent_apartment.config.CityTranslationStatic.getCityInRussianLanguage;
import static com.example.rent_apartment.constant_project.ConstantProject.*;


@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService {

    private final AddressRepository addressRepository;

    private final ApartmentRepository apartmentRepository;

    private final EntityManager  entityManager;

    private final ApplicationMapper applicationMapper;

    private final RestTemplateManager restTemplateManager;

    private final UserSession userSession;

    private final ClientRepository clientRepository;

    private final BookingHistoryRepository bookingHistoryRepository;

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public GetAddressInfoResponseDto getAddressByCity(String cityName) {
        List<ApartmentEntity> addressInformation = apartmentRepository.findApartmentEntitiesByAddressEntity_City(cityName);

        if (addressInformation.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode(ERROR_CODE_250);
            getAddressInfoResponseDto.setExceptionMessage(NOT_HAVE_APARTMENT_IN_THIS_CITY);
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
            getAddressInfoResponseDto.setExceptionCode(ERROR_CODE_255);
            getAddressInfoResponseDto.setExceptionMessage(NO_APT_WITH_THIS_PRICE + price);
            return getAddressInfoResponseDto;
        }
        return new GetAddressInfoResponseDto(apartmentEntityToDto(apartmentEntityList));
    }

    public GetAddressInfoResponseDto getApartmentByCityAndRoomsCount(String city, String roomsCount) {
        List<ApartmentEntity> apartmentEntityList = apartmentRepository.findApartmentEntitiesByRoomsCountAndAddressEntity_City(roomsCount, city);
        if (apartmentEntityList.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode(ERROR_CODE_255);
            getAddressInfoResponseDto.setExceptionMessage(NO_APARTMENT_IN_CITY_AND + city + ROOMS_COUNT + roomsCount);
            return getAddressInfoResponseDto;
        }
        return new GetAddressInfoResponseDto(apartmentEntityToDto(apartmentEntityList));
    }

    @Override
    public GetAddressInfoResponseDto getApartmentByCityAndPrice(String city, String price) {
        List<ApartmentEntity> apartmentEntityList = apartmentRepository.findApartmentEntitiesByPriceAndAddressEntity_City(price, city);
        if (apartmentEntityList.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode(ERROR_CODE_255);
            getAddressInfoResponseDto.setExceptionMessage(NO_APARTMENT_IN_CITY_AND + city + WITH_PRICE + price);
            return getAddressInfoResponseDto;
        }
        return new GetAddressInfoResponseDto(apartmentEntityToDto(apartmentEntityList));
    }

    @Override
    public GetAddressInfoResponseDto getApartmentByCityAndPriceAndRoomsCount(String city, String price, String roomsCount) {
        List<ApartmentEntity> apartmentEntitiesList = apartmentRepository.findApartmentEntitiesByRoomsCountAndPriceAndAddressEntity_City(roomsCount, price, city);
        if (apartmentEntitiesList.isEmpty()) {
            GetAddressInfoResponseDto getAddressInfoResponseDto = new GetAddressInfoResponseDto(null);
            getAddressInfoResponseDto.setExceptionCode(ERROR_CODE_255);
            getAddressInfoResponseDto.setExceptionMessage(NO_APARTMENT_IN_CITY_AND + city + WITH_PRICE + price + AND_ROOMS_COUNT + roomsCount);
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
            getAddressInfoResponseDto.setExceptionCode(ERROR_CODE_255);
            getAddressInfoResponseDto.setExceptionMessage(NO_APARTMENT_WITH_CITY_FILTER);
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
            getAddressInfoResponseDto.setExceptionCode(ERROR_CODE_255);
            getAddressInfoResponseDto.setExceptionMessage(NO_APARTMENT_WITH_RATING_FILTER);
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
            getAddressInfoResponseDto.setExceptionCode(ERROR_CODE_255);
            getAddressInfoResponseDto.setExceptionMessage(LOCATION_UNKNOWN);
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

    @Override
    public ApartmentWithMessageDto registrationNewApartment(ApartmentDto apartmentDto) {

        ApartmentEntity apartmentEntity = applicationMapper.apartmentDtoToApartmentEntity(apartmentDto);
        apartmentEntity.setRegistrationDate(LocalDateTime.now().format(formatter));
        apartmentRepository.save(apartmentEntity);
        AddressEntity AddressEntity = applicationMapper.addressDtoToAddressEntity(apartmentDto.getAddressDto());
        AddressEntity.setApartmentEntity(apartmentEntity);
        addressRepository.save(AddressEntity);

        return new ApartmentWithMessageDto(APARTMENT_SAVED, apartmentDto);

    }

    @Override
    public ApartmentWithMessageDto bookApartment(Long id, LocalDateTime start, LocalDateTime end) {

        ApartmentEntity apartmentEntityById = apartmentRepository.getApartmentEntityById(id);

        ApartmentWithMessageDto apartmentById = getApartmentById(id);
        String sessionNickName = userSession.getNickName();

        if(apartmentEntityById.getStatus().equals("false")) {
            return new ApartmentWithMessageDto("Квартира занята", null);
        }

        if(apartmentEntityById.getStatus().equals("true")) {


            apartmentEntityById.setStatus("false");
            apartmentRepository.save(apartmentEntityById);

            List<ClientApplicationEntity> clientApplicationEntitiesByNickName = clientRepository.getClientApplicationEntitiesByNickName(sessionNickName);
            ClientApplicationEntity clientApplicationEntity = clientApplicationEntitiesByNickName.get(0);

            BookingHistoryEntity bookingHistoryEntity = new BookingHistoryEntity();
            bookingHistoryEntity.setApartmentEntity(apartmentEntityById);
            bookingHistoryEntity.setClientApplicationEntity(clientApplicationEntity);
            bookingHistoryEntity.setStartDate(start);
            bookingHistoryEntity.setEndDate(end);
            bookingHistoryRepository.save(bookingHistoryEntity);

            return new ApartmentWithMessageDto("Квартира забронирована c даты " + start + " по " + end , apartmentById.getApartmentDto());
        }

        return new ApartmentWithMessageDto();
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
}
