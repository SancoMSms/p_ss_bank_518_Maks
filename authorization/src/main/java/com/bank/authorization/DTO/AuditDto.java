package com.bank.authorization.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {
    Long id;
    String entityType;
    String operationType;
    String createdBy;
    String modifiedBy;
    OffsetDateTime createdAt;
    OffsetDateTime modifiedAt;
    String newEntityJson;
    String entityJson;
}
