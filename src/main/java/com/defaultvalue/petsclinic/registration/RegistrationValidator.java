package com.defaultvalue.petsclinic.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    @Value("Duplicate.userForm.email")
    private String duplicateEmail;

    @Value("Diff.userForm.passwordConfirm")
    private String diffPassword;

    private final UserDetailsService userService;

    @Autowired
    public RegistrationValidator(UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationForm form = (RegistrationForm) target;

        if (userService.loadUserByUsername(form.getEmail()) != null) {
            errors.rejectValue("email", duplicateEmail);
        }

        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", diffPassword);
        }
    }
}
