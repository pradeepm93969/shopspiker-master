package com.shopspiker.site.modal.mapper;

import com.shopspiker.site.jpa.entity.Site;
import com.shopspiker.site.jpa.entity.TaxRate;
import com.shopspiker.site.modal.SiteModal;
import com.shopspiker.site.modal.TaxRateModal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SiteMapper {

    SiteModal toSiteDto(Site request);

    List<SiteModal> toSiteDto(List<Site> list);

    @Mapping(target = "auditSection", ignore = true)
    Site toSiteEntity(SiteModal request);

    @Mapping(target = "auditSection", ignore = true)
    Site updateSiteEntity(SiteModal request, @MappingTarget Site Site);

    TaxRateModal toTaxRateDto(TaxRate request);

    List<TaxRateModal> toTaxRateDto(List<TaxRate> list);

    @Mapping(target = "auditSection", ignore = true)
    TaxRate toTaxRateEntity(TaxRateModal request);

    @Mapping(target = "auditSection", ignore = true)
    TaxRate updateTaxRateEntity(TaxRateModal request, @MappingTarget TaxRate TaxRate);

}
