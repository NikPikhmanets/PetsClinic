package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.registration.RegistrationForm;
import com.defaultvalue.petsclinic.user.converter.UserDTO;
import com.defaultvalue.petsclinic.user.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(RegistrationForm form);

    UserDTO getUserDTO();

    List<User> getAllDoctors();

    boolean isExistUser(String email);
}
