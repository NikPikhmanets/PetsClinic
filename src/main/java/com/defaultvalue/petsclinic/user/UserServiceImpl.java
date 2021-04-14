package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.exceptions.handler.UserNotFoundException;
import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.user.converter.UserDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.role.Role;
import com.defaultvalue.petsclinic.user.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public UserDTO getUserDTO() {
        long userDetailsId = getUserDetailsId();
        Optional<User> optionalUser = userRepository.findById((int) userDetailsId);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserDTO(user);
    }

    private long getUserDetailsId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getId();
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
