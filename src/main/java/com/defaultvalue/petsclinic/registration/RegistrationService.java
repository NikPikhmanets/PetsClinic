package com.defaultvalue.petsclinic.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class RegistrationService {

    private final RegistrationValidator registrationValidator;

    @Autowired
    public RegistrationService(RegistrationValidator registrationValidator) {
        this.registrationValidator = registrationValidator;
    }

    public void validate(RegistrationForm form, BindingResult bindingResult) {
        registrationValidator.validate(form, bindingResult);
    }
}
