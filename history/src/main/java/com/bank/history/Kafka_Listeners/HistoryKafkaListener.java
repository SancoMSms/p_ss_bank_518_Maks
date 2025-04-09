package com.bank.history.Kafka_Listeners;

import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import com.bank.history.Exceptions.GlobalExceptionHandler;
import com.bank.history.Mappers.HistoryMapper;
import com.bank.history.Services.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryKafkaListener {

    private final HistoryService historyService;
    private final HistoryMapper historyMapper;
    private final HistoryKafkaProducer historyKafkaProducer;
    private final GlobalExceptionHandler globalExceptionHandler;

    @KafkaListener(topics = "${spring.kafka.topics.audit-history}", groupId = "${spring.kafka.consumer.group-id}", errorHandler = "globalExceptionHandler")
    public void consumeHistoryEvent(HistoryDto historyDto) {
        try {
            History history = historyMapper.toEntity(historyDto);
            historyService.save(history);
        } catch (Exception e) {
            ListenerExecutionFailedException exception = new ListenerExecutionFailedException("Error processing history event", e);
            globalExceptionHandler.handleError(null, exception, null);
        }
    }

    @KafkaListener(topics = "${spring.kafka.topics.audit-history-request}", groupId = "${spring.kafka.consumer.group-id}", errorHandler = "globalExceptionHandler")
    public void consumeHistoryRequest(String id) {
        try {
            HistoryDto historyDto = historyService.getHistoryById(id);
            historyKafkaProducer.sendHistoryResponse(id, historyDto);
        } catch (Exception e) {
            ListenerExecutionFailedException exception = new ListenerExecutionFailedException("Error processing history request", e);
            globalExceptionHandler.handleError(null, exception, null);
        }
    }
}