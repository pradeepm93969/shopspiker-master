package com.shopspiker.site.configuration;

import com.shopspiker.site.jpa.constants.CardTypesEnum;
import com.shopspiker.site.jpa.constants.PaymentMethodsEnum;
import com.shopspiker.site.jpa.constants.ShippingMethodsEnum;
import com.shopspiker.site.jpa.converter.PaymentMethod;
import com.shopspiker.site.jpa.converter.ShippingMethod;
import com.shopspiker.site.jpa.converter.TieredRate;
import com.shopspiker.site.jpa.repository.SiteRepository;
import com.shopspiker.site.modal.SiteModal;
import com.shopspiker.site.modal.TaxRateModal;
import com.shopspiker.site.service.SiteService;
import com.shopspiker.site.service.TaxRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Component
@Slf4j
public class SiteDbInitializer {

    @Value("${db.init.data:true}")
    private boolean initDefaultData;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteService siteService;

    @Autowired
    private TaxRateService taxRateService;

    @PostConstruct
    public void init() {
        log.info("Inside init() method of SiteDB Initializer");

        if (this.initDefaultData && siteRepository.count() == 0) {
            initializeSite();
            initializeTaxRate();
        }
    }

    private void initializeSite() {
        List<SiteModal> list = new ArrayList<>();
        list.add(SiteModal.builder()
                .id("IND")
                .name("Site for India")
                .active(true)
                .catalogId("Test123")
                .locales(Arrays.asList(new Locale("hi", "IN"), new Locale("kn", "IN")))
                .currencies(Arrays.asList(Currency.getInstance("INR")))
                .paymentMethods(Arrays.asList(
                        PaymentMethod.builder()
                                .paymentMethodType(PaymentMethodsEnum.CASH_ON_DELIVERY)
                                .rates(Arrays.asList(TieredRate.builder()
                                        .rate(new BigDecimal(100))
                                        .minAmount(new BigDecimal(0))
                                        .maxAmount(new BigDecimal(1000))
                                        .build()))
                                .build(),
                        PaymentMethod.builder()
                                .paymentMethodType(PaymentMethodsEnum.CREDIT_CARD)
                                .allowedCardTypes(Arrays.asList(CardTypesEnum.VISA,
                                        CardTypesEnum.MASTER,
                                        CardTypesEnum.AMEX,
                                        CardTypesEnum.DISCOVER))
                                .build()
                ))
                .shippingMethods(Arrays.asList(
                        ShippingMethod.builder()
                                .ShippingMethodType(ShippingMethodsEnum.STANDARD_DELIVERY)
                                .minDays(3)
                                .maxDays(5)
                                .build(),
                        ShippingMethod.builder()
                                .ShippingMethodType(ShippingMethodsEnum.EXPRESS_DELIVERY)
                                .rates(Arrays.asList(TieredRate.builder()
                                        .rate(new BigDecimal(200))
                                        .minAmount(new BigDecimal(0))
                                        .maxAmount(new BigDecimal(2000))
                                        .build()))
                                .minDays(1)
                                .maxDays(2)
                                .build(),
                        ShippingMethod.builder()
                                .ShippingMethodType(ShippingMethodsEnum.STORE_PICKUP)
                                .build()
                ))
                .build());
        list.add(SiteModal.builder()
                .id("UAE")
                .name("Site for UAE")
                .active(true)
                .catalogId("Test123")
                .locales(Arrays.asList(new Locale("en", "AE"), new Locale("ar", "AE")))
                .currencies(Arrays.asList(Currency.getInstance("AED")))
                .paymentMethods(Arrays.asList(
                        PaymentMethod.builder()
                                .paymentMethodType(PaymentMethodsEnum.CASH_ON_DELIVERY)
                                .rates(Arrays.asList(TieredRate.builder()
                                        .rate(new BigDecimal(10))
                                        .minAmount(new BigDecimal(0))
                                        .maxAmount(new BigDecimal(50))
                                        .build()))
                                .build(),
                        PaymentMethod.builder()
                                .paymentMethodType(PaymentMethodsEnum.CREDIT_CARD)
                                .allowedCardTypes(Arrays.asList(CardTypesEnum.VISA,
                                        CardTypesEnum.MASTER,
                                        CardTypesEnum.AMEX,
                                        CardTypesEnum.DISCOVER))
                                .build()
                ))
                .shippingMethods(Arrays.asList(
                        ShippingMethod.builder()
                                .ShippingMethodType(ShippingMethodsEnum.STANDARD_DELIVERY)
                                .minDays(3)
                                .maxDays(5)
                                .build(),
                        ShippingMethod.builder()
                                .ShippingMethodType(ShippingMethodsEnum.EXPRESS_DELIVERY)
                                .rates(Arrays.asList(TieredRate.builder()
                                        .rate(new BigDecimal(20))
                                        .minAmount(new BigDecimal(0))
                                        .maxAmount(new BigDecimal(200))
                                        .build()))
                                .minDays(1)
                                .maxDays(2)
                                .build(),
                        ShippingMethod.builder()
                                .ShippingMethodType(ShippingMethodsEnum.STORE_PICKUP)
                                .build()
                ))
                .build());
        list.stream().forEach(e -> siteService.create(e));
    }

    private void initializeTaxRate() {
        List<TaxRateModal> list = new ArrayList<>();
        list.add(TaxRateModal.builder()
                .id("GST")
                .name("Tax for India")
                .site(SiteModal.builder().id("IND").build())
                .rate(new BigDecimal("0.1"))
                .sortOrder(1)
                .currency(Currency.getInstance("INR"))
                .build()
        );
        list.add(TaxRateModal.builder()
                .id("VAT")
                .name("VAT for UAE")
                .site(SiteModal.builder().id("UAE").build())
                .rate(new BigDecimal("0.05"))
                .itemPriceInclusiveOfTax(false)
                .shippingPriceInclusiveOfTax(false)
                .sortOrder(1)
                .currency(Currency.getInstance("AED"))
                .build()
        );
        list.stream().forEach(e -> taxRateService.create(e));
    }

}
