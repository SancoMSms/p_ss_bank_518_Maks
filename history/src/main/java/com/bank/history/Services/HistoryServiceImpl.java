package com.bank.history.Services;

import com.bank.history.Entities.History;
import com.bank.history.Repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public History save(History history) {
        return historyRepository.save(history);
    }

    @Override
    public History getById(int id) {
        return historyRepository.getById(id);
    }
}
