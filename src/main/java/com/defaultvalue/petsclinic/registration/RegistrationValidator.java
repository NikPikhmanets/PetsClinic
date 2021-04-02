package com.defaultvalue.petsclinic.registration;

import com.defaultvalue.petsclinic.user.UserService;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.defaultvalue.petsclinic.registration.ConstantsValidator.*;

@Component
public class RegistrationValidator implements Validator {

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

    private final UserService userService;

    @Autowired
    public RegistrationValidator(UserService userService) {
        this.userService = userService;
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

        if (form.getEmail() == null || !StringUtils.hasText(form.getEmail())) {
            errors.rejectValue(EMAIL, noEmpty);
        } else if (form.getEmail().length() < 2 || form.getEmail().length() > 32) {
            errors.rejectValue(EMAIL, invalidSize);
        } else if (userService.findUserByEmail(form.getEmail()) != null) {
            errors.rejectValue(EMAIL, duplicateEmail);
        }

        if (form.getPassword() == null || !StringUtils.hasText(form.getPassword())) {
            errors.rejectValue(PASSWORD, noEmpty);
        } else if (form.getPassword().length() < 8 || form.getPassword().length() > 32) {
            errors.rejectValue(PASSWORD, shortPassword);
        }

        if (!form.getPasswordConfirm().equals(form.getPassword())) {
            errors.rejectValue(PASSWORD_CONFIRM, passwordConfirm);
        }
    }
}
