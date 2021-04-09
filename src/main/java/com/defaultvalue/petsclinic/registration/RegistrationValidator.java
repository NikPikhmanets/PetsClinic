package com.defaultvalue.petsclinic.registration;

import com.defaultvalue.petsclinic.user.UserRepository;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    private static final String FIRST_NAME = "firstName";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRM = "passwordConfirm";

    @Value("Size.userForm.noEmpty")
    private String noEmpty;

    @Value("Size.userForm.invalidSize")
    private String invalidSize;

    @Value("Duplicate.userForm.email")
    private String duplicateEmail;

    @Value("Size.userForm.shortPassword")
    private String shortPassword;

    @Value("Diff.userForm.passwordConfirm")
    private String passwordConfirm;

    private final UserRepository userRepository;

    @Autowired
    public RegistrationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationForm form = (RegistrationForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIRST_NAME, noEmpty);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, SURNAME, noEmpty);

        if (isEmpty(form.getEmail())) {
            errors.rejectValue(EMAIL, noEmpty);
        } else if (isInvalidSize(form.getEmail())) {
            errors.rejectValue(EMAIL, invalidSize);
        } else if (userRepository.findByEmail(form.getEmail()) != null) {
            errors.rejectValue(EMAIL, duplicateEmail);
        }

        if (isEmpty(form.getPassword())) {
            errors.rejectValue(PASSWORD, noEmpty);
        } else if (isInvalidSize(form.getPassword())) {
            errors.rejectValue(PASSWORD, shortPassword);
        }

        if (!form.getPasswordConfirm().equals(form.getPassword())) {
            errors.rejectValue(PASSWORD_CONFIRM, passwordConfirm);
        }
    }

    private <T extends String> boolean isInvalidSize(T t) {
        return t.length() < 8 || t.length() > 32;
    }

    private <T extends String> boolean isEmpty(T t) {
        return !StringUtils.hasText(t);
    }
}
