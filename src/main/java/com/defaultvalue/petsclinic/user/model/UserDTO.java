package com.defaultvalue.petsclinic.user.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
}
