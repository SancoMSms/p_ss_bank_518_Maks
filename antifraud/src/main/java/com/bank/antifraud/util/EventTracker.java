package com.bank.antifraud.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class EventTracker {

    private final Set<Long> processedIds = ConcurrentHashMap.newKeySet();

    public void markAsProcessed(Long id) {
        processedIds.add(id);
    }

    public boolean isProcessed(Long id) {
        return processedIds.contains(id);
    }
}