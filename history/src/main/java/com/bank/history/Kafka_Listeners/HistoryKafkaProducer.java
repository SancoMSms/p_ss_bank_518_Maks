package com.bank.history.Kafka_Listeners;

import com.bank.history.DTO.HistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryKafkaProducer {
    private final KafkaTemplate<String, HistoryDto> kafkaTemplate;

    public void sendHistoryResponse(String requestId, HistoryDto historyDto) {
        kafkaTemplate.send("audit.history.response", requestId, historyDto);
    }
}
