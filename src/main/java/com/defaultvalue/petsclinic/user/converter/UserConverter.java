package com.defaultvalue.petsclinic.user.converter;

import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .build();
    }
}
