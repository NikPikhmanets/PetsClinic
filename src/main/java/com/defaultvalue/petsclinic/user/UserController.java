package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.dto.DoctorDTO;
import com.defaultvalue.petsclinic.user.dto.UserDTO;
import com.defaultvalue.petsclinic.user.dto.UserShortInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        UserDTO userDTO = userService.getUserDTO();
        model.addAttribute("user", userDTO);

        return "profile";
    }

    @GetMapping
    @ResponseBody
    public Page<UserShortInfoDTO> users(@RequestParam(name = "page", defaultValue = "0") int page) {
        return userService.getPageableUsers(page);
    }

    @GetMapping("/doctors")
    @ResponseBody
    public Page<DoctorDTO> doctors(@RequestParam(name = "page", defaultValue = "0") int page) {
        return userService.getPageableDoctors(page);
    }
}
