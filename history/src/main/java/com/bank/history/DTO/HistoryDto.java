package com.bank.history.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {
    private Long id;
    private Long transferAuditId;
    private Long profileAuditId;
    private Long accountAuditId;
    private Long antiFraudAuditId;
    private Long publicBankInfoAuditId;
    private Long authorizationAuditId;
    private Long requestId;
    private LocalDateTime timestamp;
}
