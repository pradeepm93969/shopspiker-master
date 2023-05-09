package com.shopspiker.site.jpa.repository;

import com.shopspiker.site.jpa.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SiteRepository extends JpaRepository<Site, String>, JpaSpecificationExecutor<Site> {
}
