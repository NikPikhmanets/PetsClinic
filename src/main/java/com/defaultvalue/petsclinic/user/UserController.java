package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("1")  // TODO for test
    @ResponseBody
    public String newUser() {
        User user = getUserForTest();
        userService.saveUser(user);

        return "ok";
    }

    private User getUserForTest() {
        User user = new User();
        user.setEmail("qwerty");
        user.setPassword("$2y$12$ew9DCbbei9TIjFsL1vpOgeKIsEKZDScaLsQZlOSDjPlaTRGTbLIea"); //TODO 123
        return user;
    }
}
