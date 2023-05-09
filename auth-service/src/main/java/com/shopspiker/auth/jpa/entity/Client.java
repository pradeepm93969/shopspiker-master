package com.shopspiker.auth.jpa.entity;


import com.shopspiker.auth.jpa.constants.AuthoritiesEnum;
import com.shopspiker.auth.jpa.constants.AuthorizedGrantTypeEnum;
import com.shopspiker.auth.jpa.converter.AuthoritiesEnumListConverter;
import com.shopspiker.auth.jpa.converter.AuthorizedGrantTypeEnumListConverter;
import com.shopspiker.common.jpa.Auditable;
import com.shopspiker.common.jpa.constant.JpaConstants;
import com.shopspiker.common.jpa.entity.AuditSection;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CLIENT")
public class Client implements Auditable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false, length = JpaConstants.ID_LENGTH)
    private String id;

    @Column(name = "SECRET", nullable = false, length = JpaConstants.ID_LENGTH)
    private String secret;

    @Column(name = "AUTHORIZED_GRANT_TYPES", length = 255)
    @Convert(converter = AuthorizedGrantTypeEnumListConverter.class)
    private List<AuthorizedGrantTypeEnum> authorizedGrantTypes;

    @Column(name = "AUTHORITIES", length = 255)
    @Convert(converter = AuthoritiesEnumListConverter.class)
    private List<AuthoritiesEnum> authorities;

    @Column(name = "ACCESS_TOKEN_VALIDITY")
    private Integer accessTokenValiditySeconds;

    @Column(name = "REFRESH_TOKEN_VALIDITY")
    private Integer refreshTokenValiditySeconds;

    @Column(name = "IS_DEFAULT")
    private boolean isDefault;

    @Column(name = "ACTIVE")
    private boolean active;

    @Embedded
    private AuditSection auditSection = new AuditSection();

}
