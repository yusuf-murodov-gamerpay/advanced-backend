package com.advancedbackend.module_one.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "course")
@EntityListeners(AuditingEntityListener.class)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    Timestamp createdDate;
    @LastModifiedDate
    @Column(name = "updated_date")
    Timestamp updatedDate;
}
