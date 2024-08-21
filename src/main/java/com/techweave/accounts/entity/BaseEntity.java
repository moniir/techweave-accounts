package com.techweave.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass       //tells spring data jpa that it is the base of all entity class
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public class BaseEntity {

    @CreatedDate //for AuditorAware
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @CreatedBy //for AuditorAware
    @Column(updatable = false)
    private String createdBy;
    @LastModifiedDate //for AuditorAware
    @Column(insertable = false)
    private LocalDateTime updatedAt;
    @LastModifiedBy //for AuditorAware
    @Column(insertable = false)
    private String updatedBy;

}
