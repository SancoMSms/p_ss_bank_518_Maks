package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.Atm;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AtmMapper {

    @Named("toDto")
    AtmDto toDto(Atm atm);

    Atm toEntity(AtmDto atmDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<AtmDto> toDtoList(List<Atm> atms);
}