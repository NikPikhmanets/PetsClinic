package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.login.IUserDetailsService;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final IUserDetailsService userService;

    @Autowired
    public UserController(IUserDetailsService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping
    List<User> all() {
        return userService.findAll();
    }
}
