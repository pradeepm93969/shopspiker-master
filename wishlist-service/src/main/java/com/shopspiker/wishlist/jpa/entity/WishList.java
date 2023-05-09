package com.shopspiker.wishlist.jpa.entity;

import com.shopspiker.common.jpa.Auditable;
import com.shopspiker.common.jpa.constant.JpaConstants;
import com.shopspiker.common.jpa.converter.StringListConverter;
import com.shopspiker.common.jpa.entity.AuditSection;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "WISH_LIST")
public class WishList implements Auditable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "ssid")
    @GenericGenerator(name="ssid", strategy = "com.shopspiker.common.jpa.generator.ULIDGenerationStrategy")
    @Column(name = "ID", nullable = false, length = JpaConstants.ID_LENGTH)
    private String id;

    @Column(name = "SITE_ID", nullable = false, length = JpaConstants.SITE_ID_LENGTH)
    private String siteId;

    @Column(name = "USER_ID", nullable = false, length = JpaConstants.ID_LENGTH)
    private String userId;

    @Column(name = "PRODUCT_IDS", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> productIds = new ArrayList<>();

    @Embedded
    private AuditSection auditSection = new AuditSection();
}
