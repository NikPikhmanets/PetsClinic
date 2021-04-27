package com.defaultvalue.petsclinic.user.dto;

import com.defaultvalue.petsclinic.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserShortInfoDTO {

    private long id;
    private String name;
    private String surname;

    public UserShortInfoDTO() {
    }

    private UserShortInfoDTO(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<UserShortInfoDTO> getListUserShortInfoDTO(List<User> users) {
        return users.stream()
                .map(user -> new UserShortInfoDTO(user.getId(), user.getName(), user.getSurname()))
                .collect(Collectors.toList());
    }
}
