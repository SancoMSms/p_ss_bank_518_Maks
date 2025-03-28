package com.bank.history.Kafka_Listeners;




import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import com.bank.history.Mappers.HistoryMapper;
import com.bank.history.Services.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryKafkaProducer {
    private final KafkaTemplate<String, HistoryDto> kafkaTemplate;
    private final HistoryService historyService;
    private final HistoryMapper historyMapper;

    private static final String RESPONSE_TOPIC = "history-response";

    public void handleHistoryRequest(Long historyId, Long requestId) {
        // Достаем данные из базы
        History historyEntity = historyService.getHistoryById(historyId);
        if (historyEntity == null) {
            System.err.println("❌ История с ID " + historyId + " не найдена!");
            return;
        }

        // Конвертируем в DTO
        HistoryDto response = historyMapper.toDto(historyEntity);
        if (response == null) {
            System.err.println("❌ Ошибка маппинга для ID " + historyId);
            return;
        }

        // Добавляем requestId в ответ
        response.setRequestId(requestId);

        // Отправляем ответ в Kafka
        kafkaTemplate.send(RESPONSE_TOPIC, response);
        System.out.println("✅ Ответ отправлен в Kafka: " + response);
    }
}
