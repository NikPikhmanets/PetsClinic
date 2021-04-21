package com.defaultvalue.petsclinic.work;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.defaultvalue.petsclinic.issue.IssueController.ROLE_DOCTOR;

@Controller
@RequestMapping("/works")
@Secured(ROLE_DOCTOR)
public class WorkController {

    @GetMapping
    public String view() {
        return "works";
    }
}
