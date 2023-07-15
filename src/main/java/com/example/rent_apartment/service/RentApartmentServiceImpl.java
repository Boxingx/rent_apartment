package com.example.rent_apartment.service;

import com.example.rent_apartment.model.dto.AddressDto;
import com.example.rent_apartment.model.dto.ApartmentDto;
import com.example.rent_apartment.model.entity.AddressEntity;
import com.example.rent_apartment.model.entity.ApartmentEntity;
import com.example.rent_apartment.repository.AddressRepository;
import com.example.rent_apartment.repository.ApartmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentApartmentServiceImpl implements RentApartmentService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<AddressDto> getAddressByCity(String cityName) {
//        List<AddressEntity> addressEntityList = addressRepository.getAddressInformationByCity(cityName);
        //List<AddressEntity> addressEntityList = addressRepository.getAddressInformationByCityByJpql(cityName); //jpql
        //return prepareRequestByAddressInfo(addressEntityList);
        List<AddressEntity> resultList = getAddressInformationByCriteria(cityName);
        return prepareRequestByAddressInfo(resultList);
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

    public List<ApartmentDto> getApartmentByPrice(Long price) {
        List<ApartmentEntity> apartmentEntityList = apartmentRepository.getApartmentInfo(price);
        return apartmentEntityToDto(apartmentEntityList);
    }

    private List<ApartmentDto> apartmentEntityToDto(List<ApartmentEntity> apartmentEntityList) {

        List<ApartmentDto> resultList = new ArrayList<>();

        for (ApartmentEntity e : apartmentEntityList) {

            AddressDto addressDto = new AddressDto();
            addressDto.setCity(e.getAddressEntity().getCity());
            addressDto.setStreet(e.getAddressEntity().getStreet());
            addressDto.setBuildingNumber(e.getAddressEntity().getBuildingNumber());
            addressDto.setApartmentsNumber(e.getAddressEntity().getApartmentsNumber());

            ApartmentDto apartmentDto = new ApartmentDto();
            apartmentDto.setRoomsCount(e.getRoomsCount());
            apartmentDto.setPrice(e.getPrice());
            apartmentDto.setStatus(e.getStatus());
            apartmentDto.setAverageRating(e.getAverageRating());
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
