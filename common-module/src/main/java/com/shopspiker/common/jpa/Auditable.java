package com.shopspiker.common.jpa;

import com.shopspiker.common.jpa.entity.AuditSection;

import java.io.Serializable;

public interface Auditable extends Serializable {

    AuditSection getAuditSection();

    void setAuditSection(AuditSection audit);
}
