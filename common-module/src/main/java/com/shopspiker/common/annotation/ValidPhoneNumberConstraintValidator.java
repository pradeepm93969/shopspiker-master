package com.shopspiker.common.annotation;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.shopspiker.common.jpa.entity.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPhoneNumberConstraintValidator implements ConstraintValidator<ValidPhoneNumber, Phone> {

    @Override
    public boolean isValid(Phone phone, ConstraintValidatorContext context) {

        try {
            if (Integer.parseInt(phone.getCountryCode()) > 0 && Long.parseLong(phone.getPhoneNumber()) > 0) {
                PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
                PhoneNumber number = new PhoneNumber();
                number.setCountryCode(Integer.parseInt(phone.getCountryCode()))
                        .setNationalNumber(Long.parseLong(phone.getPhoneNumber()));
                return phoneNumberUtil.isValidNumber(number);
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
