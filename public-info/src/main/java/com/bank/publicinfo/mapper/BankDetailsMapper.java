package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankDetailsMapper {

    @Named("toDto")
    BankDetailsDto toDto(BankDetails bankDetails);

    BankDetails toEntity(BankDetailsDto bankDetailsDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<BankDetailsDto> toDtoList(List<BankDetails> bankDetailsList);
}