package com.defaultvalue.petsclinic.login;


import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.defaultvalue.petsclinic.user.Constants.PREFIX_ROLE;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userRepository = userDetailsRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        User user = optionalUser.get();
        Collection<GrantedAuthority> grantList = getGrantedAuthorities(user);

        return getUserDetails(user, grantList);
    }

    private UserDetailsImpl getUserDetails(User user, Collection<GrantedAuthority> grantList) {
        return UserDetailsImpl.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .roles(grantList).build();
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        return user.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(PREFIX_ROLE + role.getName()))
                .collect(Collectors.toSet());
    }
}
