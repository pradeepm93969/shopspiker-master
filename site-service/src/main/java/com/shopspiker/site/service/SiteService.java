package com.shopspiker.site.service;

import com.shopspiker.site.jpa.entity.Site;
import com.shopspiker.site.modal.SiteModal;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SiteService {

    public SiteModal getById(String id);
    public Page<SiteModal> get(Specification<Site> spec, Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
    public SiteModal create(SiteModal request);
    public SiteModal update(String id, SiteModal request);
    public void deleteById(String id);

    public Site findById(String id);
    public Site findByIdAndActive(String id);
}
