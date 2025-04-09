package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.*;
import com.bank.antifraud.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface SuspiciousTransferMapper {

    @SubclassMapping(source = SuspiciousPhoneTransferDto.class, target = SuspiciousPhoneTransfer.class)
    @SubclassMapping(source = SuspiciousAccountTransferDto.class, target = SuspiciousAccountTransfer.class)
    @SubclassMapping(source = SuspiciousCardTransferDto.class, target = SuspiciousCardTransfer.class)
    SuspiciousTransfer toEntity(SuspiciousTransferDto dto);

    // Маппинг Entity -> DTO
    @SubclassMapping(source = SuspiciousPhoneTransfer.class, target = SuspiciousPhoneTransferDto.class)
    @SubclassMapping(source = SuspiciousAccountTransfer.class, target = SuspiciousAccountTransferDto.class)
    @SubclassMapping(source = SuspiciousCardTransfer.class, target = SuspiciousCardTransferDto.class)
    SuspiciousTransferDto toDto(SuspiciousTransfer entity);

    // Фабричные методы для создания экземпляров конкретных подклассов
    @ObjectFactory
    default SuspiciousTransfer createEntity(SuspiciousTransferDto dto) {
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
    default SuspiciousTransferDto createDto(SuspiciousTransfer entity) {
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

    @Mapping(target = "phone_transfer_id", source = "transferId")
    SuspiciousPhoneTransfer phoneDtoToEntity(SuspiciousPhoneTransferDto dto);

    @Mapping(target = "transferId", source = "phone_transfer_id")
    SuspiciousPhoneTransferDto phoneEntityToDto(SuspiciousPhoneTransfer entity);

    @Mapping(target = "account_transfer_id", source = "transferId")
    SuspiciousAccountTransfer accountDtoToEntity(SuspiciousAccountTransferDto dto);

    @Mapping(target = "transferId", source = "account_transfer_id")
    SuspiciousAccountTransferDto accountEntityToDto(SuspiciousAccountTransfer entity);

    @Mapping(target = "card_transfer_id", source = "transferId")
    SuspiciousCardTransfer cardDtoToEntity(SuspiciousCardTransferDto dto);

    @Mapping(target = "transferId", source = "card_transfer_id")
    SuspiciousCardTransferDto cardEntityToDto(SuspiciousCardTransfer entity);
}
