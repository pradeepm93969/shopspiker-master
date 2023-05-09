package com.shopspiker.site.jpa.repository;

import com.shopspiker.site.jpa.entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaxRateRepository extends JpaRepository<TaxRate, String>, JpaSpecificationExecutor<TaxRate> {
    List<TaxRate> findBySiteId(String siteId);
}
