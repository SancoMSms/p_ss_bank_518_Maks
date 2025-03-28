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

    @KafkaListener(topics = "history-events", groupId = "history-group")
    public void consumeHistoryEvent(HistoryDto historyDto) {
        // Преобразуем DTO в Entity перед сохранением
        History history = historyMapper.toEntity(historyDto);
        historyService.save(history);
    }
}
