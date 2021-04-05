package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.entity.User;

public interface UserService {
    void saveUser(User user);

    User findUserByEmail(String email);

    User getUserById(long id);
}
