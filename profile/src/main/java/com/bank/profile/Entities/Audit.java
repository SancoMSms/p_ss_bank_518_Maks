package com.bank.profile.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
