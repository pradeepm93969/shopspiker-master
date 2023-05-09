package com.shopspiker.site.jpa.converter;

import com.shopspiker.site.jpa.constants.ShippingMethodsEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ShippingMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    private ShippingMethodsEnum ShippingMethodType;

    private List<TieredRate> rates;

    private int minDays;

    private int maxDays;

}
