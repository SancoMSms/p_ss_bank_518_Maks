package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;
import com.bank.antifraud.entities.SuspiciousCardTransfer;
import com.bank.antifraud.entities.SuspiciousAccountTransfer;
import com.bank.antifraud.entities.SuspiciousPhoneTransfer;
import com.bank.antifraud.entities.AbstractSuspiciousTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface SuspiciousTransferMapper {

    @SubclassMapping(source = SuspiciousPhoneTransferDto.class, target = SuspiciousPhoneTransfer.class)
    @SubclassMapping(source = SuspiciousAccountTransferDto.class, target = SuspiciousAccountTransfer.class)
    @SubclassMapping(source = SuspiciousCardTransferDto.class, target = SuspiciousCardTransfer.class)
    AbstractSuspiciousTransfer toEntity(AbstractSuspiciousTransferDto dto);

    // Маппинг Entity -> DTO
    @SubclassMapping(source = SuspiciousPhoneTransfer.class, target = SuspiciousPhoneTransferDto.class)
    @SubclassMapping(source = SuspiciousAccountTransfer.class, target = SuspiciousAccountTransferDto.class)
    @SubclassMapping(source = SuspiciousCardTransfer.class, target = SuspiciousCardTransferDto.class)
    AbstractSuspiciousTransferDto toDto(AbstractSuspiciousTransfer entity);

    // Фабричные методы для создания экземпляров конкретных подклассов
    @ObjectFactory
    default AbstractSuspiciousTransfer createEntity(AbstractSuspiciousTransferDto dto) {
        if (dto instanceof SuspiciousPhoneTransferDto) {
            return new SuspiciousPhoneTransfer();
        } else if (dto instanceof SuspiciousAccountTransferDto) {
            return new SuspiciousAccountTransfer();
        } else if (dto instanceof SuspiciousCardTransferDto) {
            return new SuspiciousCardTransfer();
        } else {
            throw new IllegalArgumentException("Unsupported DTO type: " + dto.getClass().getName());
        }
    }

    @ObjectFactory
    default AbstractSuspiciousTransferDto createDto(AbstractSuspiciousTransfer entity) {
        if (entity instanceof SuspiciousPhoneTransfer) {
            return new SuspiciousPhoneTransferDto();
        } else if (entity instanceof SuspiciousAccountTransfer) {
            return new SuspiciousAccountTransferDto();
        } else if (entity instanceof SuspiciousCardTransfer) {
            return new SuspiciousCardTransferDto();
        } else {
            throw new IllegalArgumentException("Unsupported Entity type: " + entity.getClass().getName());
        }
    }

    @Mapping(target = "phoneTransferId", source = "transferId")
    SuspiciousPhoneTransfer phoneDtoToEntity(SuspiciousPhoneTransferDto dto);

    @Mapping(target = "transferId", source = "phoneTransferId")
    SuspiciousPhoneTransferDto phoneEntityToDto(SuspiciousPhoneTransfer entity);

    @Mapping(target = "accountTransferId", source = "transferId")
    SuspiciousAccountTransfer accountDtoToEntity(SuspiciousAccountTransferDto dto);

    @Mapping(target = "transferId", source = "accountTransferId")
    SuspiciousAccountTransferDto accountEntityToDto(SuspiciousAccountTransfer entity);

    @Mapping(target = "cardTransferId", source = "transferId")
    SuspiciousCardTransfer cardDtoToEntity(SuspiciousCardTransferDto dto);

    @Mapping(target = "transferId", source = "cardTransferId")
    SuspiciousCardTransferDto cardEntityToDto(SuspiciousCardTransfer entity);
}
