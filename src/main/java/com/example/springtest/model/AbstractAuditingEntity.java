package com.example.springtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

    @Setter
    @Getter
    @MappedSuperclass
    @EntityListeners(AuditingEntityListener.class)
    public abstract class AbstractAuditingEntity {

        @CreatedDate
        @Column(name = "created_date", updatable = false)
        @JsonIgnore
        private Instant createdDate = Instant.now();

        @LastModifiedDate
        @Column(name = "last_modified_date")
        @JsonIgnore
        private Instant lastModifiedDate = Instant.now();
    }
