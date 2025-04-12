package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LicenseMapper {

    @Named("toDto")
    LicenseDto toDto(License license);

    License toEntity(LicenseDto licenseDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<LicenseDto> toDtoList(List<License> lisenses);
}
