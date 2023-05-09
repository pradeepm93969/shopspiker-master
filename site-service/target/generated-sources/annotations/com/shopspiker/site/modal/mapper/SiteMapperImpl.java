package com.shopspiker.site.modal.mapper;

import com.shopspiker.site.jpa.converter.PaymentMethod;
import com.shopspiker.site.jpa.converter.ShippingMethod;
import com.shopspiker.site.jpa.entity.Site;
import com.shopspiker.site.jpa.entity.TaxRate;
import com.shopspiker.site.modal.SiteModal;
import com.shopspiker.site.modal.TaxRateModal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-22T19:36:23+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class SiteMapperImpl implements SiteMapper {

    @Override
    public SiteModal toSiteDto(Site request) {
        if ( request == null ) {
            return null;
        }

        SiteModal.SiteModalBuilder siteModal = SiteModal.builder();

        siteModal.id( request.getId() );
        siteModal.name( request.getName() );
        siteModal.active( request.isActive() );
        List<Locale> list = request.getLocales();
        if ( list != null ) {
            siteModal.locales( new ArrayList<Locale>( list ) );
        }
        List<Currency> list1 = request.getCurrencies();
        if ( list1 != null ) {
            siteModal.currencies( new ArrayList<Currency>( list1 ) );
        }
        List<PaymentMethod> list2 = request.getPaymentMethods();
        if ( list2 != null ) {
            siteModal.paymentMethods( new ArrayList<PaymentMethod>( list2 ) );
        }
        List<ShippingMethod> list3 = request.getShippingMethods();
        if ( list3 != null ) {
            siteModal.shippingMethods( new ArrayList<ShippingMethod>( list3 ) );
        }
        siteModal.catalogId( request.getCatalogId() );
        siteModal.auditSection( request.getAuditSection() );

        return siteModal.build();
    }

    @Override
    public List<SiteModal> toSiteDto(List<Site> list) {
        if ( list == null ) {
            return null;
        }

        List<SiteModal> list1 = new ArrayList<SiteModal>( list.size() );
        for ( Site site : list ) {
            list1.add( toSiteDto( site ) );
        }

        return list1;
    }

    @Override
    public Site toSiteEntity(SiteModal request) {
        if ( request == null ) {
            return null;
        }

        Site site = new Site();

        site.setId( request.getId() );
        site.setName( request.getName() );
        site.setActive( request.isActive() );
        List<Locale> list = request.getLocales();
        if ( list != null ) {
            site.setLocales( new ArrayList<Locale>( list ) );
        }
        List<Currency> list1 = request.getCurrencies();
        if ( list1 != null ) {
            site.setCurrencies( new ArrayList<Currency>( list1 ) );
        }
        List<PaymentMethod> list2 = request.getPaymentMethods();
        if ( list2 != null ) {
            site.setPaymentMethods( new ArrayList<PaymentMethod>( list2 ) );
        }
        List<ShippingMethod> list3 = request.getShippingMethods();
        if ( list3 != null ) {
            site.setShippingMethods( new ArrayList<ShippingMethod>( list3 ) );
        }
        site.setCatalogId( request.getCatalogId() );

        return site;
    }

    @Override
    public Site updateSiteEntity(SiteModal request, Site Site) {
        if ( request == null ) {
            return Site;
        }

        Site.setId( request.getId() );
        Site.setName( request.getName() );
        Site.setActive( request.isActive() );
        if ( Site.getLocales() != null ) {
            List<Locale> list = request.getLocales();
            if ( list != null ) {
                Site.getLocales().clear();
                Site.getLocales().addAll( list );
            }
            else {
                Site.setLocales( null );
            }
        }
        else {
            List<Locale> list = request.getLocales();
            if ( list != null ) {
                Site.setLocales( new ArrayList<Locale>( list ) );
            }
        }
        if ( Site.getCurrencies() != null ) {
            List<Currency> list1 = request.getCurrencies();
            if ( list1 != null ) {
                Site.getCurrencies().clear();
                Site.getCurrencies().addAll( list1 );
            }
            else {
                Site.setCurrencies( null );
            }
        }
        else {
            List<Currency> list1 = request.getCurrencies();
            if ( list1 != null ) {
                Site.setCurrencies( new ArrayList<Currency>( list1 ) );
            }
        }
        if ( Site.getPaymentMethods() != null ) {
            List<PaymentMethod> list2 = request.getPaymentMethods();
            if ( list2 != null ) {
                Site.getPaymentMethods().clear();
                Site.getPaymentMethods().addAll( list2 );
            }
            else {
                Site.setPaymentMethods( null );
            }
        }
        else {
            List<PaymentMethod> list2 = request.getPaymentMethods();
            if ( list2 != null ) {
                Site.setPaymentMethods( new ArrayList<PaymentMethod>( list2 ) );
            }
        }
        if ( Site.getShippingMethods() != null ) {
            List<ShippingMethod> list3 = request.getShippingMethods();
            if ( list3 != null ) {
                Site.getShippingMethods().clear();
                Site.getShippingMethods().addAll( list3 );
            }
            else {
                Site.setShippingMethods( null );
            }
        }
        else {
            List<ShippingMethod> list3 = request.getShippingMethods();
            if ( list3 != null ) {
                Site.setShippingMethods( new ArrayList<ShippingMethod>( list3 ) );
            }
        }
        Site.setCatalogId( request.getCatalogId() );

        return Site;
    }

    @Override
    public TaxRateModal toTaxRateDto(TaxRate request) {
        if ( request == null ) {
            return null;
        }

        TaxRateModal.TaxRateModalBuilder taxRateModal = TaxRateModal.builder();

        taxRateModal.id( request.getId() );
        taxRateModal.name( request.getName() );
        taxRateModal.active( request.isActive() );
        taxRateModal.rate( request.getRate() );
        taxRateModal.currency( request.getCurrency() );
        List<String> list = request.getProductTypes();
        if ( list != null ) {
            taxRateModal.productTypes( new ArrayList<String>( list ) );
        }
        taxRateModal.shippingPriceInclusiveOfTax( request.isShippingPriceInclusiveOfTax() );
        taxRateModal.itemPriceInclusiveOfTax( request.isItemPriceInclusiveOfTax() );
        taxRateModal.sortOrder( request.getSortOrder() );
        taxRateModal.site( toSiteDto( request.getSite() ) );
        taxRateModal.auditSection( request.getAuditSection() );

        return taxRateModal.build();
    }

    @Override
    public List<TaxRateModal> toTaxRateDto(List<TaxRate> list) {
        if ( list == null ) {
            return null;
        }

        List<TaxRateModal> list1 = new ArrayList<TaxRateModal>( list.size() );
        for ( TaxRate taxRate : list ) {
            list1.add( toTaxRateDto( taxRate ) );
        }

        return list1;
    }

    @Override
    public TaxRate toTaxRateEntity(TaxRateModal request) {
        if ( request == null ) {
            return null;
        }

        TaxRate taxRate = new TaxRate();

        taxRate.setId( request.getId() );
        taxRate.setName( request.getName() );
        taxRate.setActive( request.isActive() );
        taxRate.setRate( request.getRate() );
        List<String> list = request.getProductTypes();
        if ( list != null ) {
            taxRate.setProductTypes( new ArrayList<String>( list ) );
        }
        taxRate.setCurrency( request.getCurrency() );
        taxRate.setShippingPriceInclusiveOfTax( request.isShippingPriceInclusiveOfTax() );
        taxRate.setItemPriceInclusiveOfTax( request.isItemPriceInclusiveOfTax() );
        taxRate.setSortOrder( request.getSortOrder() );
        taxRate.setSite( toSiteEntity( request.getSite() ) );

        return taxRate;
    }

    @Override
    public TaxRate updateTaxRateEntity(TaxRateModal request, TaxRate TaxRate) {
        if ( request == null ) {
            return TaxRate;
        }

        TaxRate.setId( request.getId() );
        TaxRate.setName( request.getName() );
        TaxRate.setActive( request.isActive() );
        TaxRate.setRate( request.getRate() );
        if ( TaxRate.getProductTypes() != null ) {
            List<String> list = request.getProductTypes();
            if ( list != null ) {
                TaxRate.getProductTypes().clear();
                TaxRate.getProductTypes().addAll( list );
            }
            else {
                TaxRate.setProductTypes( null );
            }
        }
        else {
            List<String> list = request.getProductTypes();
            if ( list != null ) {
                TaxRate.setProductTypes( new ArrayList<String>( list ) );
            }
        }
        TaxRate.setCurrency( request.getCurrency() );
        TaxRate.setShippingPriceInclusiveOfTax( request.isShippingPriceInclusiveOfTax() );
        TaxRate.setItemPriceInclusiveOfTax( request.isItemPriceInclusiveOfTax() );
        TaxRate.setSortOrder( request.getSortOrder() );
        if ( request.getSite() != null ) {
            if ( TaxRate.getSite() == null ) {
                TaxRate.setSite( new Site() );
            }
            updateSiteEntity( request.getSite(), TaxRate.getSite() );
        }
        else {
            TaxRate.setSite( null );
        }

        return TaxRate;
    }
}
