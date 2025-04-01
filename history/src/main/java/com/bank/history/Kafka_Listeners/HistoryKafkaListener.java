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
    @KafkaListener(topics = "${spring.kafka.topics.audit-history}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeHistoryEvent(HistoryDto historyDto) {
        History history = historyMapper.toEntity(historyDto);
        historyService.save(history);
    }

    // Обработка запросов на получение истории изменений
    @KafkaListener(topics = "${spring.kafka.topics.audit-history-request}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeHistoryRequest(String requestId) {
        HistoryDto historyDto = historyService.getHistoryByRequestId(requestId);
        historyService.sendHistoryResponse(requestId, historyDto);
    }
}
