package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.exceptions.handler.UserNotFoundException;
import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.user.converter.UserDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private int userId;
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void init() {
        userId = 2;

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(userId);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);

        userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        userService = new UserServiceImpl(userRepository, roleRepository);
    }

    @Test
    public void getUserDTO_shouldReturnUserDTO() {
        int id = 2;
        String name = "name";
        String surname = "surname";
        String email = "email";
        String phone = "phone";
        LocalDate localDate = LocalDate.of(2020, 11, 1);

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setBirthday(localDate);

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