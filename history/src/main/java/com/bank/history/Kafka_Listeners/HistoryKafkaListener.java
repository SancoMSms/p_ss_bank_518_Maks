package com.bank.history.Kafka_Listeners;

import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import com.bank.history.Mappers.HistoryMapper;
import com.bank.history.Services.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryKafkaListener {

    private final HistoryService historyService;
    private final HistoryMapper historyMapper;
    private final HistoryKafkaProducer historyKafkaProducer;

    // TODO Обработка входящих событий аудита
    @KafkaListener(topics = "${spring.kafka.topics.audit-history}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeHistoryEvent(HistoryDto historyDto) {
        log.info("Получено сообщение в 'audit-history': {}", historyDto);
        long startTime = System.currentTimeMillis();

        try {
            History history = historyMapper.toEntity(historyDto);
            historyService.save(history);
            log.info("Сообщение успешно обработано и сохранено в БД");
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения: {}", historyDto, e);
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("Время обработки сообщения: {} мс", (endTime - startTime));
        }
    }

    // TODO Обработка запросов на получение истории изменений
    @KafkaListener(topics = "${spring.kafka.topics.audit-history-request}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeHistoryRequest(String id) {
        log.info("Получен запрос на историю изменений: requestId={}", id);
        long startTime = System.currentTimeMillis();

        try {
            HistoryDto historyDto = historyService.getHistoryById(id);
            historyKafkaProducer.sendHistoryResponse(id, historyDto);
            log.info(" запрос успешно обработан: {}", (id));
        } catch (Exception e) {
            log.error("Ошибка при обработке запроса истории: requestId={}", id, e);
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("Время обработки запроса: {} мс", (endTime - startTime));
        }
    }
}