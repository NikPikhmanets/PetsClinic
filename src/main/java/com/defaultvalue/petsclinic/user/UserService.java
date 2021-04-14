package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.converter.UserDTO;
import com.defaultvalue.petsclinic.user.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    UserDTO getUserDTO();

    List<User> getAllDoctors();

    boolean isExistUser(String email);
}
