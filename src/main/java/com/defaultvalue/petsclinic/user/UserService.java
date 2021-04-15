package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.exceptions.UserAlreadyExistException;
import com.defaultvalue.petsclinic.registration.RegistrationForm;
import com.defaultvalue.petsclinic.user.converter.UserDTO;
import com.defaultvalue.petsclinic.user.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(RegistrationForm registrationForm) throws UserAlreadyExistException;

    UserDTO getUserDTO();

    List<User> getAllDoctors();
}
