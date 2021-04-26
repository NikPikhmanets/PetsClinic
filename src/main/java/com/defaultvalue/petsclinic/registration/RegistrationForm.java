package com.defaultvalue.petsclinic.registration;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class RegistrationForm {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String surname;

    @NotEmpty
    @Email
    private String email;
    private String phoneNumber;
    private LocalDate birthday;

    @NotNull
    @Size(min = 2, max = 16)
    private String password;

    @NotNull
    @Size(min = 2, max = 16)
    private String passwordConfirm;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
