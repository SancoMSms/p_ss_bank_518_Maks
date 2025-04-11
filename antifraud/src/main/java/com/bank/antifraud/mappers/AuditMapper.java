package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entities.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toDto(Audit entity);
    Audit toEntity(AuditDto dto);
    String dtoToString(SuspiciousPhoneTransferDto suspiciousPhoneTransferDto);
    String dtoToString(SuspiciousAccountTransferDto suspiciousAccountTransferDto);
    String dtoToString(SuspiciousCardTransferDto suspiciousCardTransferDto);
}
