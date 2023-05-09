package com.shopspiker.site.service.impl;

import com.shopspiker.common.web.exception.CustomApplicationException;
import com.shopspiker.site.jpa.entity.Site;
import com.shopspiker.site.jpa.repository.SiteRepository;
import com.shopspiker.site.modal.SiteModal;
import com.shopspiker.site.modal.mapper.SiteMapper;
import com.shopspiker.site.service.SiteService;
import com.shopspiker.site.service.validator.SiteValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepository repository;

    @Autowired
    private SiteValidator siteValidator;

    @Autowired
    private SiteMapper mapper;

    @Override
    @Cacheable(value = "Site", key = "#id")
    public SiteModal getById(String id) {
        return mapper.toSiteDto(findById(id));
    }

    @Override
    public Page<SiteModal> get(Specification<Site> spec, Integer pageNo, Integer pageSize, String sortBy,
                                 String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize,
                Sort.by((StringUtils.isBlank(sortDirection) || sortDirection.equalsIgnoreCase("asc")) ? Sort.Direction.ASC
                        : Sort.Direction.DESC, sortBy));
        Page<Site> sitePage = repository.findAll(spec, pageable);
        return new PageImpl<>(mapper.toSiteDto(sitePage.getContent()), pageable,
                sitePage.getTotalElements());

    }

    @Override
    public SiteModal create(SiteModal request) {
        if (repository.findById(request.getId()).isPresent())
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "ID_TAKEN", "Id Taken");

        request.getLocales().stream().forEach(locale -> siteValidator.validateLocale(locale));

        Site site = repository.save(mapper.toSiteEntity(request));
        return mapper.toSiteDto(site);
    }

    @Override
    @CachePut(value = "Site", key = "#id")
    public SiteModal update(String id, SiteModal request) {

        request.getLocales().stream().forEach(locale -> siteValidator.validateLocale(locale));

        Site site = findById(id);
        request.setId(id);
        site = repository.save(mapper.updateSiteEntity(request, site));

        return mapper.toSiteDto(site);
    }

    @Override
    @CacheEvict(value = "Site", key = "#id")
    public void deleteById(String id) {
        repository.delete(findById(id));
    }

    @Override
    public Site findById(String id) {
        return repository.findById(id).orElseThrow(() -> new CustomApplicationException(HttpStatus.NOT_FOUND,
                "Site_NOT_FOUND", "Site not found"));
    }

    @Override
    public Site findByIdAndActive(String id) {
        Site site = findById(id);
        if (!site.isActive()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "Site_NOT_ACTIVE",
                    "Site not active");
        }
        return site;
    }

}

