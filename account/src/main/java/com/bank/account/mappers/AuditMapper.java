package com.bank.account.mappers;

import com.bank.account.dto.AuditDto;
import com.bank.account.entities.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditMapper {

    Audit toEntity (AuditDto auditDto);
}
