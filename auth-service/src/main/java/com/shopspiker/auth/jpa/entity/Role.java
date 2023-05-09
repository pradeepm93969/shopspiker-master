package com.shopspiker.auth.jpa.entity;

import java.util.List;

import com.shopspiker.auth.jpa.constants.PermissionsEnum;
import com.shopspiker.auth.jpa.converter.PermissionsEnumListConverter;
import jakarta.persistence.*;

import com.shopspiker.common.jpa.constant.JpaConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopspiker.common.jpa.Auditable;
import com.shopspiker.common.jpa.entity.AuditSection;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ROLE")
public class Role implements Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false, length = JpaConstants.ID_LENGTH)
    private String id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "PERMISSIONS", length = 2000)
    @Convert(converter = PermissionsEnumListConverter.class)
    private List<PermissionsEnum> permissions;

    @Embedded
    private AuditSection auditSection = new AuditSection();

}