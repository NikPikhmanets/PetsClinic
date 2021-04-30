package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.config.WithCustomUserDetails;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.role.Role;
import com.defaultvalue.petsclinic.user.role.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @WithCustomUserDetails(email = "admin@pc.com")
    void fetchAllUsersAsAdmin_thenGetPageable() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(4))
                .andExpect(jsonPath("$.content[0].id").value(5))
                .andExpect(jsonPath("$.content[0].name").value("test-user-4"))
                .andExpect(jsonPath("$.content[0].surname").value("test-surname-4"))
                .andExpect(jsonPath("$.content[3].id").value(1))
                .andExpect(jsonPath("$.content[3].name").value("Nik"))
                .andExpect(jsonPath("$.content[3].surname").value("Petrov"));
    }

    @Test
    @WithCustomUserDetails(email = "admin@pc.com")
    void fetchAllUsersAsAdminNotExistPage_thenGetPageable() throws Exception {
        mockMvc.perform(get("/users?page=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    void fetchAllUsersWithoutAuthentication_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithCustomUserDetails(email = "email-test1@mail")
    void fetchAllUsersWithoutAdminRole_thenForbidden() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithCustomUserDetails(email = "email-test1@mail")
    void fetchAllDoctorsWithoutAdminRole_thenForbidden() throws Exception {
        mockMvc.perform(get("/users/doctors"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithCustomUserDetails(email = "admin@pc.com")
    void fetchAllDoctorsAsAdmin_thenGetPageable() throws Exception {
        mockMvc.perform(get("/users/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].id").value(2))
                .andExpect(jsonPath("$.content[0].name").value("test-user-1"))
                .andExpect(jsonPath("$.content[0].surname").value("test-surname-1"));
    }

    @Test
    @WithCustomUserDetails(email = "admin@pc.com")
    void fetchAllDoctorsAsAdminNOtExistPage_thenGetPageable() throws Exception {
        mockMvc.perform(get("/users/doctors?page=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    @WithCustomUserDetails(email = "admin@pc.com")
    void setDoctorForUserById_thenStatusOk() throws Exception {
        mockMvc.perform(put("/users/4/enable-role-doctor/specialty/2"))
                .andExpect(status().isOk());

        Optional<User> optionalUser = userRepository.findById(4L);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(4, (long) user.getId());
        assertEquals(2, (long) user.getSpecialties().getId());

        Role doctor = roleRepository.findByName("DOCTOR");
        assertEquals("DOCTOR", doctor.getName());

        Set<Role> roles = user.getRoles();
        assertTrue(roles.contains(doctor));
    }

    @Test
    @WithCustomUserDetails(email = "admin@pc.com")
    void disableDoctorForUserById_thenStatusOk() throws Exception {
        mockMvc.perform(put("/users/4/disable-role-doctor"))
                .andExpect(status().isOk());

        Optional<User> optionalUser = userRepository.findById(4L);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(4, (long) user.getId());
        assertNull(user.getSpecialties());

        Role doctor = roleRepository.findByName("DOCTOR");
        assertEquals("DOCTOR", doctor.getName());

        Set<Role> roles = user.getRoles();
        assertFalse(roles.contains(doctor));
    }

    @Test
    @WithCustomUserDetails(email = "email-test1@mail")
    void setDoctorForUserByIdWithoutAdminRole_thenForbidden() throws Exception {
        mockMvc.perform(get("/users/4/enable-role-doctor/specialty/2"))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    @WithCustomUserDetails(email = "email-test1@mail")
    void disableDoctorForUserByIdWithoutAdminRole_thenForbidden() throws Exception {
        mockMvc.perform(get("/users/4/disable-role-doctor"))
                .andExpect(status().isMethodNotAllowed());
    }
}