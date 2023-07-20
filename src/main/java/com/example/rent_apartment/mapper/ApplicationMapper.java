package com.example.rent_apartment.mapper;

import com.example.rent_apartment.model.dto.AddressDto;
import com.example.rent_apartment.model.dto.ApartmentDto;
import com.example.rent_apartment.model.entity.AddressEntity;
import com.example.rent_apartment.model.entity.ApartmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    //    @Mapping(target = "cityInfo", source = "city")
//    @Mapping(target = "cityInfo", ignore = true)
//    @Mapping(target = "city", ignore = true)
    AddressDto addressEntityToAddressDto(AddressEntity addressEntity);

    //    @AfterMapping
//    default void prepareCityField(AddressEntity addressEntity, @MappingTarget AddressDto addressDto) {
//        addressDto.setCity("Значение города изменено после обработки");
//    }

    ApartmentDto apartmentEntityToApartmentDto(ApartmentEntity apartmentEntity);


}
