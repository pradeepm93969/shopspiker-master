package com.shopspiker.site.service.impl;

import com.shopspiker.common.web.exception.CustomApplicationException;
import com.shopspiker.site.jpa.entity.TaxRate;
import com.shopspiker.site.jpa.repository.TaxRateRepository;
import com.shopspiker.site.modal.TaxRateModal;
import com.shopspiker.site.modal.mapper.SiteMapper;
import com.shopspiker.site.service.SiteService;
import com.shopspiker.site.service.TaxRateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxRateServiceImpl implements TaxRateService {

    @Autowired
    private TaxRateRepository repository;

    @Autowired
    private SiteMapper mapper;

    @Autowired
    private SiteService siteService;

    @Override
    @Cacheable(value = "TaxRate", key = "#id")
    public TaxRateModal getById(String id) {
        return mapper.toTaxRateDto(findById(id));
    }

    @Override
    public Page<TaxRateModal> get(Specification<TaxRate> spec, Integer pageNo, Integer pageSize, String sortBy,
                                  String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize,
                Sort.by((StringUtils.isBlank(sortDirection) || sortDirection.equalsIgnoreCase("asc")) ? Sort.Direction.ASC
                        : Sort.Direction.DESC, sortBy));
        Page<TaxRate> taxRatePage = repository.findAll(spec, pageable);
        return new PageImpl<>(mapper.toTaxRateDto(taxRatePage.getContent()), pageable,
                taxRatePage.getTotalElements());

    }

    @Override
    public TaxRateModal create(TaxRateModal request) {
        if (repository.findById(request.getId()).isPresent())
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "ID_TAKEN", "Id Taken");

        request.setSite(siteService.getById(request.getSite().getId()));

        TaxRate taxRate = repository.save(mapper.toTaxRateEntity(request));
        return mapper.toTaxRateDto(taxRate);
    }

    @Override
    @CachePut(value = "TaxRate", key = "#id")
    public TaxRateModal update(String id, TaxRateModal request) {
        TaxRate taxRate = findById(id);
        request.setId(id);
        request.setSite(siteService.getById(request.getSite().getId()));
        taxRate = repository.save(mapper.updateTaxRateEntity(request, taxRate));

        return mapper.toTaxRateDto(taxRate);
    }

    @Override
    @CacheEvict(value = "TaxRate", key = "#id")
    public void deleteById(String id) {
        repository.delete(findById(id));
    }

    @Override
    public TaxRate findById(String id) {
        return repository.findById(id).orElseThrow(() -> new CustomApplicationException(HttpStatus.NOT_FOUND,
                "TaxRate_NOT_FOUND", "TaxRate not found"));
    }

    @Override
    public List<TaxRate> findBySiteId(String id) {
        return repository.findBySiteId(id);
    }

}

