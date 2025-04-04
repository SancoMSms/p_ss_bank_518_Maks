package com.bank.history.Services;

import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;
import com.bank.history.Kafka_Listeners.HistoryKafkaProducer;
import com.bank.history.Mappers.HistoryMapper;
import com.bank.history.Repositories.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryKafkaProducer historyKafkaProducer;
    private final HistoryMapper historyMapper;

    @Override
    public History save(History history) {
        log.info("Сохранение истории: {}", history);
        try {
            historyRepository.save(history);
            log.info("История успешно сохранена: id={}", history.getId());
            return history;
        } catch (Exception e) {
            log.error("Ошибка при сохранении истории: {}", history, e);
            throw new RuntimeException("Ошибка при сохранении истории", e); //TODO прикидываем исключения выше
        }
    }

    @Override
    public HistoryDto getHistoryById(String id) {
        log.info("Поиск истории по requestId={}", id);
        try {
            Optional<History> historyOptional = historyRepository.findById(Long.parseLong(id));
            if (historyOptional.isPresent()) {
                return historyMapper.toDto(historyOptional.get());
            } else {
                log.warn("История не найдена для requestId={}", id);
                return null;
            }
        } catch (NumberFormatException e) {
            log.error("Некорректный формат requestId: {}", id, e);
            throw new RuntimeException("Некорректный формат requestId: " + id, e);
        } catch (Exception e) {
            log.error("Ошибка при получении истории по requestId={}", id, e);
            throw new RuntimeException("Ошибка при получении истории по requestId: " + id, e);
        }
    }

    @Override
    public void sendHistoryResponse(String requestId, HistoryDto historyDto) {
        log.info("Отправка истории в Kafka: requestId={}, данные={}", requestId, historyDto);
        try {
            historyKafkaProducer.sendHistoryResponse(requestId, historyDto);
            log.info("История успешно отправлена: requestId={}", requestId);
        } catch (Exception e) {
            log.error("Ошибка при отправке истории в Kafka: requestId={}, данные={}", requestId, historyDto, e);
            throw new RuntimeException();
        }
    }
}