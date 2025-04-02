package com.bank.authorization.Mappers;

import com.bank.authorization.DTO.AuditDto;
import com.bank.authorization.Entities.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditMapper {
    AuditDto toAuditDto (Audit audit);
    Audit toAudit(AuditDto auditDto);
    List<AuditDto> toAuditDtoList(List<Audit> audits);
}
