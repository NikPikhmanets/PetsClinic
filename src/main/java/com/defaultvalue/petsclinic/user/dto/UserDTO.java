package com.defaultvalue.petsclinic.user.dto;


import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Value.Immutable
public abstract class UserDTO {

    public abstract String getName();

    public abstract String getSurname();

    public abstract String getEmail();

    @Nullable
    public abstract String getPhoneNumber();

    @Nullable
    public abstract LocalDate getBirthday();
}
