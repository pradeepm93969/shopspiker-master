package com.shopspiker.auth.jpa.entity;

import com.shopspiker.common.jpa.Auditable;
import com.shopspiker.common.jpa.constant.JpaConstants;
import com.shopspiker.common.jpa.converter.PhoneConverter;
import com.shopspiker.common.jpa.entity.AuditSection;
import com.shopspiker.common.jpa.entity.Phone;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "OTP_DETAILS")
public class Otp implements Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "ssid")
    @GenericGenerator(name="ssid", strategy = "com.shopspiker.common.jpa.generator.ULIDGenerationStrategy")
    @Column(name = "ID", nullable = false, length = JpaConstants.ID_LENGTH)
    private String id;

    @Column(name = "PHONE", length = 50)
    @Convert(converter = PhoneConverter.class)
    private Phone phone;

    @Column(name = "OTP", nullable = false, length = 100)
    private String otp;

    @Column(name = "NUMBER_OF_RETRIES")
    private int noOfRetries;

    @Column(name = "OTP_VERIFIED")
    private boolean otpVerified;

    @Column(name = "VALID_TILL", columnDefinition = "TIMESTAMP", nullable = false)
    private OffsetDateTime validTill;

    @Embedded
    private AuditSection auditSection = new AuditSection();

}
