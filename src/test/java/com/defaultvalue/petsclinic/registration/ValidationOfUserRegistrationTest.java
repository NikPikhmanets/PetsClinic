package com.defaultvalue.petsclinic.registration;

import com.defaultvalue.petsclinic.exceptions.UserAlreadyExistException;
import com.defaultvalue.petsclinic.user.UserService;
import com.defaultvalue.petsclinic.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ValidationOfUserRegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void checkPersonInfoWhenAllMissingThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "")
                .param("surname", "")
                .param("email", "")
                .param("password", "")
                .param("passwordConfirm", "");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenFirstNameIsMissingThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "")
                .param("surname", "bb")
                .param("email", "aa@bb")
                .param("password", "123")
                .param("passwordConfirm", "123");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenSurnameIsMissingThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "")
                .param("email", "aa@bb")
                .param("password", "123")
                .param("passwordConfirm", "123");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenEmailIsMissingThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "bb")
                .param("email", "")
                .param("password", "123")
                .param("passwordConfirm", "123");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenPasswordIsMissingThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "bb")
                .param("email", "aa@bb")
                .param("password", "")
                .param("passwordConfirm", "123");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenConfirmPasswordIsMissingThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "bb")
                .param("email", "aa@bb")
                .param("password", "123")
                .param("passwordConfirm", "");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenEmailIsWrongThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "bb")
                .param("email", "aabb")
                .param("password", "123")
                .param("passwordConfirm", "1234");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenConfirmPasswordIsWrongThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "bb")
                .param("email", "aa@bb")
                .param("password", "123")
                .param("passwordConfirm", "1234");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenPasswordIsShortThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "bb")
                .param("email", "aa@bb")
                .param("password", "1")
                .param("passwordConfirm", "1");

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenEmailIsExistThenFailure() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "bb")
                .param("email", "aa@bb")
                .param("password", "123")
                .param("passwordConfirm", "123");
        when(userService.saveUser(any(RegistrationForm.class))).thenThrow(UserAlreadyExistException.class);

        mockMvc.perform(createPerson)
                .andExpect(model().hasErrors())
                .andExpect(view().name("registration"));
    }

    @Test
    public void checkPersonInfoWhenValidRequestThenSuccess() throws Exception {
        MockHttpServletRequestBuilder createPerson = post("/registration")
                .param("firstName", "aa")
                .param("surname", "bb")
                .param("email", "aa@bb")
                .param("password", "123")
                .param("passwordConfirm", "123");
        given(userService.saveUser(any(RegistrationForm.class))).willReturn(any(User.class));

        mockMvc.perform(createPerson)
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/login"));
    }
}