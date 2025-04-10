package com.bank.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuditDto {

    @NotNull(message = "id не может быть null")
    private Long id;

    @NotBlank(message = "entityType не может быть пустым")
    private String entityType;

    @NotBlank(message = "operationType не может быть пустым")
    private String operationType;

    @NotBlank(message = "createdBy не может быть пустым")
    private String createdBy;

    private String modifiedBy;

    @NotNull(message = "createdAt не может быть null")
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @NotBlank(message = "newEntityJson не может быть пустым")
    private String newEntityJson;

    private String entityJson;
}
