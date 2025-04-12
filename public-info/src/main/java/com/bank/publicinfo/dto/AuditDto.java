package com.bank.publicinfo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {

    private Long id;

    @NotBlank(message = "entityType must not be blank")
    @Size(max = 50, message = "entityType must not exceed 50 characters")
    private String entityType;

    @NotBlank(message = "operationType must not be blank")
    @Size(max = 50, message = "operationType must not exceed 50 characters")
    private String operationType;

    @NotBlank(message = "createdBy must not be blank")
    @Size(max = 100, message = "createdBy must not exceed 100 characters")
    private String createdBy;

    @NotBlank(message = "modifiedBy must not be blank")
    @Size(max = 100, message = "modifiedBy must not exceed 100 characters")
    private String modifiedBy;

    @NotNull(message = "createdAt must not be null")
    private Date createdAt;

    @NotNull(message = "modifiedAt must not be null")
    private Date modifiedAt;

    @NotBlank(message = "newEntityJson must not be blank")
    private String newEntityJson;

    @NotBlank(message = "entityJson must not be blank")
    private String entityJson;
}
