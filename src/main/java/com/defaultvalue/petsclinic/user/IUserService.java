package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.role.Role;
import com.defaultvalue.petsclinic.role.RoleRepository;
import com.defaultvalue.petsclinic.role.UsersRoles;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.defaultvalue.petsclinic.user.Constants.ID_ROLE_USER;

@Service
public class IUserService implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public IUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(User user) {
        updateUser(user);
        User newUser = userRepository.save(user);
        setRoleForNewUser(newUser);
    }

    private void updateUser(User user) {
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
    }

    private void setRoleForNewUser(User user) {
        if (isNotNull(user)) {
            UsersRoles usersRoles = new UsersRoles();
            usersRoles.setUserId(user.getId());
            usersRoles.setRoleId(ID_ROLE_USER);

//            roleRepository.insertRoleForUser();
        }
    }

    private <T> boolean isNotNull(T t) {
        return t != null;
    }
}
