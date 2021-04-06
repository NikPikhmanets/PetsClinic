package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.user.converter.UserConverter;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/profile")
    public String me(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     Model model) {
        if (userDetails == null) {
            return "/login";
        }
        User user = userService.getUserById(userDetails.getId());
        UserDTO userDTO = userConverter.toUserDto(user);
        model.addAttribute("user", userDTO);

        return "profile";
    }
}
