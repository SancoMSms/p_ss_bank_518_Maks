package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificateMapper {

    @Named("toDto")
    CertificateDto toDto(Certificate certificate);

    Certificate toEntity(CertificateDto certificateDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<CertificateDto> toDtoList(List<Certificate> certificates);
}
