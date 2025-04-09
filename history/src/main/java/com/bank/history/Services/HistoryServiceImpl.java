package com.bank.history.Services;

import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import com.bank.history.Kafka_Listeners.HistoryKafkaProducer;
import com.bank.history.Mappers.HistoryMapper;
import com.bank.history.Repositories.HistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryKafkaProducer historyKafkaProducer;
    private final HistoryMapper historyMapper;

    @Override
    public History save(History history) {
        try {
            return historyRepository.save(history);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось сохранить историю", e);
        }
    }

    @Override
    public HistoryDto getHistoryById(String id) {
        try {
            long parsedId;
            try {
                parsedId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new ValidationException("Некорректный формат id: " + id);
            }

            Optional<History> historyOptional = historyRepository.findById(parsedId);
            return historyOptional
                    .map(historyMapper::toDto)
                    .orElseThrow(() -> new EntityNotFoundException("История не найдена для id: " + id));

        } catch (ValidationException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении истории по id: " + id, e);
        }
    }

    @Override
    @Async
    public void publishHistoryResponseToKafka(String id, HistoryDto historyDto) {
        try {
            historyKafkaProducer.sendHistoryResponse(id, historyDto);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при отправке истории в Kafka: id: " + id, e);
        }
    }
}