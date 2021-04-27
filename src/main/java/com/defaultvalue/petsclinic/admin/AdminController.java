package com.defaultvalue.petsclinic.admin;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.defaultvalue.petsclinic.admin.AdminController.ROLE_ADMIN;

@Controller
@RequestMapping("admin")
@Secured(ROLE_ADMIN)
public class AdminController {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @GetMapping
    public String pageOfAdmin() {
        return "admin";
    }
}
