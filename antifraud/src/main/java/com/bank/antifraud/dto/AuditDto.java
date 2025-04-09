package com.bank.antifraud.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuditDto {

    private Long id;

    private String entity_type;

    private String operation_type;

    private String created_by;

    private String modified_by;

    private Timestamp created_at;

    private Timestamp modified_at;

    private String new_entity_json;

    private String entity_json;

    private Long transfer_id;
}
