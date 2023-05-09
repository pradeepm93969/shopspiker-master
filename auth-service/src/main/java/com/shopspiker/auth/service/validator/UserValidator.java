package com.shopspiker.auth.service.validator;

import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.jpa.repository.UserRepository;
import com.shopspiker.common.jpa.entity.Phone;
import com.shopspiker.common.web.exception.CustomApplicationException;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    public void isPhoneNumberAvailable(Phone phone) {
        Optional<User> optionalUser = userRepository.findByPhone(phone);
        if (optionalUser.isPresent()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "PHONE_TAKEN", "Phone taken");
        }
    }

    public void isEmailAvailable(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "EMAIL_TAKEN", "Email taken");
        }
    }

    public void isValidPassword(String password) {
        PasswordValidator validator = new PasswordValidator(null, Arrays.asList(
                // length between 8 and 16 characters
                new LengthRule(8, 16),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),
                // no whitespace
                new WhitespaceRule(),
                // rejects passwords that contain a sequence of >= 5 characters alphabetical
                // (e.g. abcdef)
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                // rejects passwords that contain a sequence of >= 5 characters numerical (e.g.
                // 12345)
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)));
        RuleResult result = validator.validate(new PasswordData(password));
        if (!result.isValid()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "PASSWORD_INVALID",
                    "Password does not match the rule");
        }
    }
}
