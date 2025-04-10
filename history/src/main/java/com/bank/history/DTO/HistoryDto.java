package com.bank.history.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class HistoryDto {
    @PositiveOrZero
    @NotNull
    private Long id;

    @PositiveOrZero
    @NotNull
    private Long transferAuditId;

    @PositiveOrZero
    @NotNull
    private Long profileAuditId;

    @PositiveOrZero
    @NotNull
    private Long accountAuditId;

    @PositiveOrZero
    @NotNull
    private Long antiFraudAuditId;

    @PositiveOrZero
    @NotNull
    private Long publicBankInfoAuditId;

    @PositiveOrZero
    @NotNull
    private Long authorizationAuditId;

    @PositiveOrZero
    @NotNull
    private Long requestId;

    @PositiveOrZero
    @NotNull
    private LocalDateTime timestamp;
}
