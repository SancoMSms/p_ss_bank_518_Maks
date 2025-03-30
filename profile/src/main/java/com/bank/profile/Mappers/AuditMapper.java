package com.bank.profile.Mappers;

import com.bank.profile.DTO.AuditDto;
import com.bank.profile.Entities.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);

    AuditDto toDTO(Audit audit);

    Audit toEntity(AuditDto auditDto);
}
