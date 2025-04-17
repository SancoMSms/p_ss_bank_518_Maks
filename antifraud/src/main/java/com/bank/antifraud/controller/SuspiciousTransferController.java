package com.bank.antifraud.controller;

import com.bank.antifraud.dto.TransferAntiFraudDto;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.services.SuspiciousTransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
public class SuspiciousTransferController {

    private final SuspiciousTransferService suspiciousTransferService;

    @PostMapping("/create")
    public ResponseEntity<String> createTransfer(@RequestBody @Valid TransferAntiFraudDto kafkaDto) {
        try {
            suspiciousTransferService.createSuspiciousTransfer(kafkaDto);
            return ResponseEntity.ok().build();
        } catch (ValidationException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }
}
