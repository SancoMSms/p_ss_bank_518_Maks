package com.bank.profile.Mappers;

import com.bank.profile.DTO.AuditDto;
import com.bank.profile.Entities.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    AuditDto toDTO(Audit audit);

    Audit toEntity(AuditDto auditDto);
}
