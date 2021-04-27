package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.dto.DoctorDTO;
import com.defaultvalue.petsclinic.user.dto.UserDTO;
import com.defaultvalue.petsclinic.user.dto.UserShortInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.defaultvalue.petsclinic.admin.AdminController.ROLE_ADMIN;

@Controller
@RequestMapping("/users")
@Secured(ROLE_ADMIN)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    @Secured("ROLE_USER")
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

    @PutMapping("/{id}/enable-role-doctor/specialty/{specialtyId}")
    @ResponseStatus(HttpStatus.OK)
    public void enableRoleDoctor(@PathVariable Long id,
                                 @PathVariable Long specialtyId) {
        userService.enableRoleDoctor(id, specialtyId);
    }

    @PutMapping("/{id}/disable-role-doctor")
    @ResponseStatus(HttpStatus.OK)
    public void updateRoleDoctor(@PathVariable Long id) {
        userService.disableRoleDoctor(id);
    }
}
