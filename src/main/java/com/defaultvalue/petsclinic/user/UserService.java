package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    User findUserByEmail(String email);

    User getUserById(long id);

    List<User> getAllDoctors();
}
