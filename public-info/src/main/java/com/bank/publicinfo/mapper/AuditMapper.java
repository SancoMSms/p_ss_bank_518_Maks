package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.Audit;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditMapper {

    @Named("toDto")
    AuditDto toDto(Audit audit);

    Audit toEntity(AuditDto auditDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<AuditDto> toDtoList(List<Audit> audits);
}