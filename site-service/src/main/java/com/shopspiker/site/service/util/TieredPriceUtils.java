package com.shopspiker.site.service.util;

import com.shopspiker.site.jpa.converter.TieredRate;

import java.math.BigDecimal;
import java.util.List;

public class TieredPriceUtils {

    public static BigDecimal getTieredRate(List<TieredRate> tieredRates, BigDecimal amount) {
        BigDecimal rate = null;
        for (TieredRate tieredRate : tieredRates) {
            if (tieredRate.getMinAmount().compareTo(amount) >= 0 &&
                    (tieredRate.getMaxAmount().compareTo(amount) <= 0 || tieredRate.getMaxAmount() == null)) {
                rate = tieredRate.getRate();
                break;
            }
        }
        return rate;
    }
}
