package com.shopspiker.common.service.validation;

import com.shopspiker.common.web.exception.CustomApplicationException;
import com.shopspiker.common.jpa.entity.Phone;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.http.HttpStatus;

public class PhoneValidator {

    public static void validate(Phone phone) {
        try {
            Integer countryCode = Integer.parseInt(phone.getCountryCode());
            Long nationalNumber = Long.parseLong(phone.getPhoneNumber());

            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = new Phonenumber.PhoneNumber();
            number.setCountryCode(countryCode).setNationalNumber(nationalNumber);

            if (!phoneNumberUtil.isValidNumber(number)) {
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "INVALID_PHONE", "Invalid phone number");
            }
        } catch (Exception ex) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "INVALID_PHONE", "Invalid phone number");
        }
    }
}
