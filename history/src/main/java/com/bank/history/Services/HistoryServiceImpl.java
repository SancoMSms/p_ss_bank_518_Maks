package com.bank.history.Services;

import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import com.bank.history.Kafka_Listeners.HistoryKafkaProducer;
import com.bank.history.Mappers.HistoryMapper;
import com.bank.history.Repositories.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryKafkaProducer historyKafkaProducer;
    private final HistoryMapper historyMapper;

    @Override
    public void save(History history) {
        historyRepository.save(history);
    }

    @Override
    public HistoryDto getHistoryByRequestId(String requestId) {
        Optional<History> historyOptional = historyRepository.findById(Long.parseLong(requestId));
        return historyOptional.map(historyMapper::toDto).orElse(null);
    }

    @Override
    public void sendHistoryResponse(String requestId, HistoryDto historyDto) {
        historyKafkaProducer.sendHistoryResponse(requestId, historyDto);
    }
}