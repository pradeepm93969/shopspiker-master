package com.shopspiker.site.service;

import com.shopspiker.site.jpa.entity.TaxRate;
import com.shopspiker.site.modal.TaxRateModal;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TaxRateService {

    public TaxRateModal getById(String id);
    public Page<TaxRateModal> get(Specification<TaxRate> spec, Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
    public TaxRateModal create(TaxRateModal request);
    public TaxRateModal update(String id, TaxRateModal request);
    public void deleteById(String id);

    public TaxRate findById(String id);
    public List<TaxRate> findBySiteId(String id);
}
