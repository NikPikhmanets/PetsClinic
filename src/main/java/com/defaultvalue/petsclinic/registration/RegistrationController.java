package com.defaultvalue.petsclinic.registration;

import com.defaultvalue.petsclinic.exceptions.UserAlreadyExistException;
import com.defaultvalue.petsclinic.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final RegistrationValidator registrationValidator;
    private final UserService userService;

    @Autowired
    public RegistrationController(RegistrationValidator registrationValidator, UserService userService) {
        this.registrationValidator = registrationValidator;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder wdb) {
        wdb.addValidators(registrationValidator);
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid RegistrationForm registrationForm,
                               BindingResult bindingResult) throws UserAlreadyExistException {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveUser(registrationForm);

        return "redirect:/login";
    }
}
