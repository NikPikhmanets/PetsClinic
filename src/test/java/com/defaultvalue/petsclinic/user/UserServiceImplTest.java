package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.exceptions.UserNotFoundException;
import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.user.dto.UserDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.role.RoleRepository;
import com.defaultvalue.petsclinic.user.specialty.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private long userId;
    private final String name = "name";
    private final String surname = "surname";
    private final String email = "email";
    private final String phone = "phone";
    private final String password = "password";
    private final LocalDate localDate = LocalDate.of(2020, 11, 1);

    private User user;

    private UserRepository userRepository;
    private SpecialtyRepository specialtyRepository;
    private UserService userService;

    @BeforeEach
    public void init() {
        userId = 2;

        user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setBirthday(localDate);
        user.setPassword(password);

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(userId);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);

        userRepository = mock(UserRepository.class);
        specialtyRepository = mock(SpecialtyRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, roleRepository, specialtyRepository, bCryptPasswordEncoder);
    }

    @Test
    public void getUserDTO_shouldReturnUserDTO() {
        long id = 2;
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        UserDTO userDTO = userService.getUserDTO();
        assertEquals(name, userDTO.getName());
        assertEquals(surname, userDTO.getSurname());
        assertEquals(email, userDTO.getEmail());
        assertEquals(phone, userDTO.getPhoneNumber());
        assertEquals(userDTO.getBirthday(), localDate);
    }

    @Test
    public void getUserDTO_IfUserNotFound_shouldThrowException() {
        String expectedMessage = "User not found";

        given(userRepository.findById(userId)).willReturn(Optional.empty());
        String actualMessage = assertThrows(UserNotFoundException.class, userService::getUserDTO).getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}