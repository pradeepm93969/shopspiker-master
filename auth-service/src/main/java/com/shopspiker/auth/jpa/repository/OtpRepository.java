package com.shopspiker.auth.jpa.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shopspiker.auth.jpa.entity.Otp;
import com.shopspiker.common.jpa.entity.Phone;

public interface OtpRepository extends JpaRepository<Otp, String>, JpaSpecificationExecutor<Otp> {

    public Optional<Otp> findByPhoneAndOtpVerifiedAndValidTillAfter(Phone phone, boolean otpVerified, OffsetDateTime validTill);

    public Optional<Otp> findByPhone(Phone phone);

}
