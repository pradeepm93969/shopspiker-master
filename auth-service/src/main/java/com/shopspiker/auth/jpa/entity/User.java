package com.shopspiker.auth.jpa.entity;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.shopspiker.common.jpa.Auditable;
import com.shopspiker.common.jpa.constant.JpaConstants;
import com.shopspiker.common.jpa.converter.PhoneConverter;
import com.shopspiker.common.jpa.entity.AuditSection;
import com.shopspiker.common.jpa.entity.Phone;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USER",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_USER_EMAIL", columnNames = {"EMAIL"}),
                @UniqueConstraint(name = "UK_PHONE", columnNames = {"PHONE"})
        },
        indexes = {
                @Index(columnList = "PHONE"),
                @Index(columnList = "EMAIL")
        }
)
public class User implements Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "ssid")
    @GenericGenerator(name="ssid", strategy = "com.shopspiker.common.jpa.generator.ULIDGenerationStrategy")
    @Column(name = "ID", nullable = false, length = JpaConstants.ID_LENGTH)
    private String id;

    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String lastName;

    @Column(name = "PHONE", length = 100)
    @Convert(converter = PhoneConverter.class)
    private Phone phone;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "LAST_SUCCESSFUL_LOGIN_AT", columnDefinition = "TIMESTAMP")
    private OffsetDateTime lastSuccessfulLoginAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_ROLE"))},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ROLE_USER"))})
    private List<Role> roles;

    @Embedded
    private AuditSection auditSection = new AuditSection();
}
