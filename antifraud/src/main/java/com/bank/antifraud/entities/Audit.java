package com.bank.antifraud.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private String entity_type;

    @Column(nullable = false)
    private String operation_type;

    @Column(nullable = false)
    private String created_by;

    private String modified_by;

    @Column(nullable = false)
    private Timestamp created_at;

    private Timestamp modified_at;

    private String new_entity_json;

    @Column(nullable = false)
    private String entity_json;
}
