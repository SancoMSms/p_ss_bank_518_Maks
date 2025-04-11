package com.bank.antifraud.controller;

import com.bank.antifraud.dto.TransferAntiFraudDto;
import com.bank.antifraud.services.SuspiciousTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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
        return ResponseEntity.ok().build();
    }

    // Обновление подозрительного перевода
    @PutMapping("/update")
    public ResponseEntity<Void> updateTransfer(@RequestBody TransferAntiFraudDto kafkaDto) {
        suspiciousTransferService.updateSuspiciousTransfer(kafkaDto);
        return ResponseEntity.ok().build();
    }

    // Удаление подозрительного перевода
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteTransfer(@RequestBody TransferAntiFraudDto kafkaDto) {
        suspiciousTransferService.deleteSuspiciousTransfer(kafkaDto);
        return ResponseEntity.ok().build();
    }
}
