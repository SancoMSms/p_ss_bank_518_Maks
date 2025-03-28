package com.bank.history.Services;

import com.bank.history.Entities.History;
import com.bank.history.Repositories.HistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }
@Transactional
    @Override
    public History save(History history) {
        return historyRepository.save(history);
    }
@Transactional
    @Override
    public History getHistoryById(long id) {
        return historyRepository.getById(id);
    }
@Transactional
    @Override
    public List<History> getAll() {
        return historyRepository.findAll();
    }
}
