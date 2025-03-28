package com.bank.history.Services;

import com.bank.history.Entities.History;

import java.util.List;

public interface HistoryService {
    History save(History history);
    History getHistoryById(long id);
    List<History> getAll();
}
