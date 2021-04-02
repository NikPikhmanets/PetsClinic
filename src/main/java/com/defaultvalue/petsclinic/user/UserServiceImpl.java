package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.role.Role;
import com.defaultvalue.petsclinic.user.role.RoleRepository;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.defaultvalue.petsclinic.user.Constants.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(User user) {
        User updateUser = updateUser(user);
        userRepository.save(updateUser);
    }

    private User updateUser(User user) {
        Role role = getRoleUser();
        user.setRoles(Collections.singleton(role));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    private Role getRoleUser() {
        return roleRepository.findByName(ROLE_USER);
    }
}
