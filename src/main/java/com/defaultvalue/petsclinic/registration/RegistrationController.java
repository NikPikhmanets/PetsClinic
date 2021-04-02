package com.defaultvalue.petsclinic.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final RegistrationValidator registrationValidator;

    @Autowired
    public RegistrationController(RegistrationValidator registrationValidator) {
        this.registrationValidator = registrationValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("registrationForm") RegistrationForm form,
                               BindingResult bindingResult) {
        registrationValidator.validate(form, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        return "redirect:/info";
    }
}
