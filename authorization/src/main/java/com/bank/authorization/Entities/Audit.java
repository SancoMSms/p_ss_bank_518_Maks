package com.bank.authorization.Entities;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

/*
Класс Audit обычно используется в контексте аудита
(отслеживания изменений данных) в приложениях,
чтобы записывать информацию о том,
 кто и когда изменил данные в базе данных.
*/
@NoArgsConstructor  // Конструктор без параметров
@AllArgsConstructor  // Конструктор со всеми полями
@Getter
@Setter
@Entity
@Table(name = "audit")
public class Audit {

    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    @Column(name = "entity_type")
    private String entityType;

    @NotNull
    @Column(name = "operation_type")
    private String operationType;

    @NotNull
    @Column(name = "created_by")
    private String createdBy;

    @NotNull
    @Column(name = "modified_by")
    private String modifiedBy;

    @NotNull
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @NotNull
    @Column(name = "modified_at")
    private OffsetDateTime modifiedAt;

    @NotNull
    @Column(name = "new_entity_json")
    private String newEntityJson;

    @NotNull
    @Column(name = "entity_json")
    private String entityJson;


}

