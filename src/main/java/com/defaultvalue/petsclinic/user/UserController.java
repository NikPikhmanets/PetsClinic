package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.converter.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (userDetails == null) {
            return "/login";
        }
        UserDTO userDTO = userService.getUserById(userDetails.getId());
        model.addAttribute("user", userDTO);

        return "profile";
    }

    @GetMapping("/doctors")
    @ResponseBody
    public List<User> doctors() {
        return userService.getAllDoctors();
    }
}
