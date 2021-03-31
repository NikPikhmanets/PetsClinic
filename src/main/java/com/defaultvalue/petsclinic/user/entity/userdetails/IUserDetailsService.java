package com.defaultvalue.petsclinic.user.entity.userdetails;


import com.defaultvalue.petsclinic.role.Role;
import com.defaultvalue.petsclinic.role.RoleRepository;
import com.defaultvalue.petsclinic.user.UserRepository;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.defaultvalue.petsclinic.user.Constants.PREFIX_ROLE;

@Service
public class IUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public IUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        User user = optionalUser.get();
        Collection<GrantedAuthority> grantList = getGrantedAuthorities(user);

        return getUserDetails(user, grantList);
    }

    private IUserDetails getUserDetails(User user, Collection<GrantedAuthority> grantList) {
        return IUserDetails.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roles(grantList).build();
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        List<Role> roles = roleRepository.findRolesByUserId(user.getId());

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(PREFIX_ROLE + role.getName()))
                .collect(Collectors.toSet());
    }
}
