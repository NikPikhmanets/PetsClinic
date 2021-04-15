package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.dto.ImmutableUserDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@Import(UserController.class)
class UserControllerTest {

    private LocalDate testDate;
    private List<User> users;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void init() {
        testDate = LocalDate.of(2020, 11, 1);

        users = new ArrayList<>();

        for (long i = 1; i <= 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("username" + i);
            user.setSurname("surname" + i);
            user.setEmail("email" + i);
            user.setPhoneNumber("phoneNumber" + i);
            users.add(user);
        }
    }

    @Test
    public void shouldReturnProfilePageIfNoAuthentication() throws Exception {
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setEmail("email");
        user.setPhoneNumber("phone");
        user.setBirthday(testDate);

        ImmutableUserDTO userDTO = ImmutableUserDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .build();

        assertEquals("name", userDTO.getName());
        assertEquals("surname", userDTO.getSurname());
        assertEquals("email", userDTO.getEmail());
        assertEquals("phone", userDTO.getPhoneNumber());
        assertEquals(userDTO.getBirthday(), testDate);

        given(userService.getUserDTO()).willReturn(userDTO);

        mockMvc.perform(get("/users/profile"))
                .andExpect(view().name("profile"));
    }

    @Test
    void shouldFetchAllUsers() throws Exception {
        given(userService.getAllDoctors()).willReturn(users);

        this.mockMvc.perform(get("/users/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(users.size())));
    }

    @Configuration
    @EnableWebSecurity
    static class TestSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests().anyRequest().anonymous();
        }
    }
}