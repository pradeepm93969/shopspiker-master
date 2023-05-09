package com.shopspiker.common.jpa.entity;

import jakarta.validation.Payload;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Phone implements Payload, Serializable {

    private static final long serialVersionUID = 1L;

    private String countryCode;

    private String phoneNumber;

    private String extension;
}
