package com.defaultvalue.petsclinic.registration;

import com.defaultvalue.petsclinic.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Value("Duplicate.userForm.email")
    private String duplicateEmail;

    @Value("Diff.userForm.passwordConfirm")
    private String diffPassword;

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid RegistrationForm registrationForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (userService.isExistUser(registrationForm.getEmail())) {
            bindingResult.rejectValue("email", duplicateEmail);
            return "registration";
        }
        if (!registrationForm.isPasswordsEquals()) {
            bindingResult.rejectValue("passwordConfirm", diffPassword);
            return "registration";
        }
        userService.saveUser(registrationForm);

        return "redirect:/login";
    }
}
