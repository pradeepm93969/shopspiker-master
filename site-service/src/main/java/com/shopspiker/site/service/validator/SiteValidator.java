package com.shopspiker.site.service.validator;

import com.shopspiker.common.web.exception.CustomApplicationException;
import com.shopspiker.site.configuration.SiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;

@Component
public class SiteValidator {

    @Autowired
    private SiteConfig siteConfig;

    public void validateLocale(Locale locale) {
        if (locale.getCountry().isEmpty() || locale.getLanguage().isEmpty()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "INVALID_LOCALE",
                    "Locale " + locale.toString() + " must contain both Language and Country");
        }
        if (!Arrays.asList(Locale.getAvailableLocales()).contains(locale)
                && !siteConfig.getCustomLocales().contains(locale.toString())) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "INVALID_LOCALE",
                    "Locale " + locale.toString() + " not supported");
        }
    }

}
