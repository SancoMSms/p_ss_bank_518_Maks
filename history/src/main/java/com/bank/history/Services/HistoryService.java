package com.bank.history.Services;

import com.bank.history.Entities.History;

interface HistoryService {
    History save(History history);
    History getById(int id);
    History getAll();
}
