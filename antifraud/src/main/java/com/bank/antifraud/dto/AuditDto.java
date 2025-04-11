package com.bank.antifraud.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class AuditDto {

    private static final int MAX_SIZE = 40;

    private Long id;

    @NotNull
    @Size(max = MAX_SIZE)
    private String entityType;

    @NotNull
    private String operationType;

    @NotNull
    private String createdBy;

    private String modifiedBy;

    @NotNull
    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String newEntityJson;

    @NotNull
    private String entityJson;

    private Long transferId;
}
