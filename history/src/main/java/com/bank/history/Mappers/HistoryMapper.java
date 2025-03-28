package com.bank.history.Mappers;


import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HistoryMapper {
    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    HistoryDto toDto(History entity);

    History toEntity(HistoryDto dto);
}