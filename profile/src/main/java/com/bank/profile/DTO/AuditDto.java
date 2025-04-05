package com.bank.profile.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {
    private Long id;
    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private Date createdAt;
    private Date modifiedAt;
    private String newEntityJson;
    private String entityJson;
}
