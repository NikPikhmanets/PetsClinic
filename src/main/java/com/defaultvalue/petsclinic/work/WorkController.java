package com.defaultvalue.petsclinic.work;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/works")
public class WorkController {

    @GetMapping
    public String view() {
        return "works";
    }
}
