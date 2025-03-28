package com.bank.history.Mappers;


import ch.qos.logback.core.model.ComponentModel;
import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    HistoryDto toDto(History entity);

    History toEntity(HistoryDto dto);
}