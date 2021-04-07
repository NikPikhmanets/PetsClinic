package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.role.Role;
import com.defaultvalue.petsclinic.user.role.RoleRepository;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllDoctors() {
        return buildTestUsers();
    }

    private List<User> buildTestUsers() {
        List<User> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("username" + (i * 31));
            user.setSurname("surname" + (i * 31));
            user.setEmail("email" + (i * 31));
            user.setPhoneNumber("phoneNumber" + (i * 31));
            list.add(user);
        }
        return list;
    }
}
