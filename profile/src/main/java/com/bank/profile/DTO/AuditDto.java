package com.bank.profile.DTO;

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

    @NotBlank(message = "entityType не может быть пустым")
    @Size(max = 50, message = "entityType не может быть длиннее 50 символов")
    private String entityType;

    @NotBlank(message = "operationType не может быть пустым")
    @Size(max = 50, message = "operationType не может быть длиннее 50 символов")
    private String operationType;

    @NotBlank(message = "createdBy не может быть пустым")
    @Size(max = 100, message = "createdBy не может быть длиннее 100 символов")
    private String createdBy;

    @NotBlank(message = "modifiedBy не может быть пустым")
    @Size(max = 100, message = "modifiedBy не может быть длиннее 100 символов")
    private String modifiedBy;

    @NotNull(message = "createdAt не может быть null")
    private Date createdAt;

    @NotNull(message = "modifiedAt не может быть null")
    private Date modifiedAt;

    @NotBlank(message = "newEntityJson не может быть пустым")
    private String newEntityJson;

    @NotBlank(message = "entityJson не может быть пустым")
    private String entityJson;
}
