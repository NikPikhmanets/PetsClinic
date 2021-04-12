package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.converter.UserDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.role.Role;
import com.defaultvalue.petsclinic.user.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(User user) {
        Role role = roleRepository.findByName(ROLE_USER);
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
    }

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id);

        return new UserDTO(user);
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
