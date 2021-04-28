package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.dto.DoctorDTO;
import com.defaultvalue.petsclinic.user.dto.ImmutableUserDTO;
import com.defaultvalue.petsclinic.user.dto.UserShortInfoDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.specialty.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@Import(UserController.class)
class UserControllerTest {

    private final int SIZE_PAGE = 10;

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
            Specialty specialty = new Specialty();
            specialty.setName("specialty" + i);

            User user = new User();
            user.setId(i);
            user.setName("username" + i);
            user.setSurname("surname" + i);
            user.setSpecialties(specialty);
            users.add(user);
        }
    }

    @Test
    public void shouldReturnProfileWithUserInfo() throws Exception {
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
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("user", hasProperty("email", is("email"))))
                .andExpect(model().attribute("user", hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("user", hasProperty("email", is("email"))))
                .andExpect(model().attribute("user", hasProperty("phoneNumber", is("phone"))))
                .andExpect(model().attribute("user", hasProperty("birthday", is(testDate))));
    }

    @Test
    void shouldFetchAllUsers() throws Exception {
        List<UserShortInfoDTO> listUserShortInfoDTO = new UserShortInfoDTO().getListUserShortInfoDTO(users);
        PageImpl<UserShortInfoDTO> userShortInfoDTOS = new PageImpl<>(listUserShortInfoDTO, PageRequest.of(0, SIZE_PAGE), users.size());
        when(userService.getPageableUsers(anyInt())).thenReturn(userShortInfoDTOS);

        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(10))
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("username1"))
                .andExpect(jsonPath("$.content[0].surname").value("surname1"));
    }

    @Test
    void shouldFetchAllDoctors() throws Exception {
        List<DoctorDTO> listUserShortInfoDTO = new DoctorDTO().getListDoctorDTO(users);
        Page<DoctorDTO> doctorDTOS = new PageImpl<>(listUserShortInfoDTO, PageRequest.of(0, SIZE_PAGE), users.size());
        when(userService.getPageableDoctors(anyInt())).thenReturn(doctorDTOS);

        this.mockMvc.perform(get("/users/doctors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(10))
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("username1"))
                .andExpect(jsonPath("$.content[0].surname").value("surname1"))
                .andExpect(jsonPath("$.content[0].specialty").value("specialty1"));
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