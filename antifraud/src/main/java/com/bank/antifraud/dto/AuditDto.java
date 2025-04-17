package com.bank.antifraud.dto;

import com.bank.antifraud.enums.TransferType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {

    private static final int MAX_SIZE = 40;

    private Long id;

    @NotNull
    @Size(max = MAX_SIZE)
    private TransferType entityType;

    @NotNull
    private String operationType;

    private Long transferId;

    @NotNull
    private String createdBy;

    private String modifiedBy;

    @NotNull
    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String newEntityJson;

    @NotNull
    private String entityJson;

}
