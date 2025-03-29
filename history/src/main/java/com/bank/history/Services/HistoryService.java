package com.bank.history.Services;

import com.bank.history.DTO.HistoryDto;
import com.bank.history.Entities.History;

public interface HistoryService {
    void save(History history);
    HistoryDto getHistoryByRequestId(String requestId);
    void sendHistoryResponse(String requestId, HistoryDto historyDto);
}
