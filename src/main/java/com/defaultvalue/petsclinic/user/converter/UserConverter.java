package com.defaultvalue.petsclinic.user.converter;

import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDTO toUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(userDTO.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setBirthday(user.getBirthday());

        return userDTO;
    }
}
