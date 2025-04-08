package com.bank.antifraud.controller;

import com.bank.antifraud.dto.TransferAntiFraudDto;
import com.bank.antifraud.services.SuspiciousTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
public class SuspiciousTransferController {

    private final SuspiciousTransferService suspiciousTransferService;

    public SuspiciousTransferController(SuspiciousTransferService suspiciousTransferService) {
        this.suspiciousTransferService = suspiciousTransferService;
    }

    // Создание подозрительного перевода
    @PostMapping("/create")
    public ResponseEntity<Void> createTransfer(@RequestBody TransferAntiFraudDto kafkaDto) {
        suspiciousTransferService.createSuspiciousTransfer(kafkaDto);
        return ResponseEntity.status(201).build();
    }

    // Обновление подозрительного перевода
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateTransfer(@PathVariable Long id,
                                               @RequestBody TransferAntiFraudDto kafkaDto) {
        suspiciousTransferService.updateSuspiciousTransfer(kafkaDto);
        return ResponseEntity.ok().build();
    }

    // Удаление подозрительного перевода
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransfer(@PathVariable Long id,
                                               @RequestBody TransferAntiFraudDto kafkaDto) {
        suspiciousTransferService.deleteSuspiciousTransfer(kafkaDto);
        return ResponseEntity.noContent().build();
    }
}