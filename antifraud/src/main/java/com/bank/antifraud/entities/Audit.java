package com.bank.antifraud.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityType;

    private String operationType;

    private String createdBy;

    private String modifiedBy;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String newEntityJson;

    private String entityJson;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Audit audit = (Audit) o;
        return Objects.equals(id, audit.id) &&
                Objects.equals(entityType, audit.entityType) &&
                Objects.equals(operationType, audit.operationType) &&
                Objects.equals(createdBy, audit.createdBy) &&
                Objects.equals(modifiedBy, audit.modifiedBy) &&
                Objects.equals(createdAt, audit.createdAt) &&
                Objects.equals(modifiedAt, audit.modifiedAt) &&
                Objects.equals(newEntityJson, audit.newEntityJson) &&
                Objects.equals(entityJson, audit.entityJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                entityType,
                operationType,
                createdBy,
                modifiedBy,
                createdAt,
                modifiedAt,
                newEntityJson,
                entityJson);
    }
}
