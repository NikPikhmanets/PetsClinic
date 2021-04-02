package com.defaultvalue.petsclinic.registration;

import com.defaultvalue.petsclinic.user.UserService;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Size.userForm.noEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Size.userForm.noEmpty");

        if (form.getEmail() == null || !StringUtils.hasText(form.getEmail())) {
            errors.rejectValue("email", "Size.userForm.noEmpty");
        } else if (form.getEmail().length() < 2 || form.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.userForm.size");
        } else if (userService.findUserByEmail(form.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        if (form.getPassword() == null || !StringUtils.hasText(form.getPassword())) {
            errors.rejectValue("password", "Size.userForm.noEmpty");
        } else if (form.getPassword().length() < 8 || form.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!form.getPasswordConfirm().equals(form.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
