package com.bank.history.Kafka_Listeners;

import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import com.bank.history.Mappers.HistoryMapper;
import com.bank.history.Services.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryKafkaListener {

    private final HistoryService historyService;
    private final HistoryMapper historyMapper;

    // Обработка входящих событий аудита
    @KafkaListener(topics = "audit.history", groupId = "history-group")
    public void consumeHistoryEvent(HistoryDto historyDto) {
        History history = historyMapper.toEntity(historyDto);
        historyService.save(history);
    }

    // Обработка запросов на получение истории изменений
    @KafkaListener(topics = "audit.history.request", groupId = "history-group")
    public void consumeHistoryRequest(String requestId) {
        HistoryDto historyDto = historyService.getHistoryByRequestId(requestId);
        historyService.sendHistoryResponse(requestId, historyDto);
    }
}
