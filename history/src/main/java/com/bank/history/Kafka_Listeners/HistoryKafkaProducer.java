package com.bank.history.Kafka_Listeners;

import com.bank.history.DTO.HistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryKafkaProducer {
    private final KafkaTemplate<String, HistoryDto> kafkaTemplate;
    @Value("${spring.kafka.topics.audit-history-response}")
    private String auditHistoryResponseTopic;

    public void sendHistoryResponse(String requestId, HistoryDto historyDto) {
        log.info("Отправка ответа в топик '{}': requestId={}, данные={}", auditHistoryResponseTopic, requestId, historyDto);
        try {
            kafkaTemplate.send(auditHistoryResponseTopic, requestId, historyDto);
            log.info("Сообщение успешно отправлено: requestId={}", requestId);
        }catch (Exception e){
            log.error("Ошибка при отправке сообщения: requestId={}, данные={}", requestId, historyDto, e);
        }
    }
}
