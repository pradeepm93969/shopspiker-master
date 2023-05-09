package com.shopspiker.site.jpa.converter;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TieredRate implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal rate;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

}
