package com.defaultvalue.petsclinic.registration;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationForm {

    private String firstName;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
    private String password;
    private String passwordConfirm;
}
