package com.bank.history.Services;

import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;

public interface HistoryService {
    History save(History history);
    HistoryDto getHistoryById(String id);
    void sendHistoryResponse(String requestId, HistoryDto historyDto);
}
