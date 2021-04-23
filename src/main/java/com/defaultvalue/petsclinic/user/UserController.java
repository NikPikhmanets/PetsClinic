package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.dto.DoctorDTO;
import com.defaultvalue.petsclinic.user.dto.UserDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        UserDTO userDTO = userService.getUserDTO();
        model.addAttribute("user", userDTO);

        return "profile";
    }

    @GetMapping("/doctors")
    @ResponseBody
    public List<DoctorDTO> doctors() {
        return new DoctorDTO().getListDoctorDTO(userRepository.findAll());
    }
}
