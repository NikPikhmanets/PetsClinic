package com.defaultvalue.petsclinic.user.dto;

import com.defaultvalue.petsclinic.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorDTO {

    private long id;
    private String name;
    private String surname;
    private String specialty;

    public DoctorDTO() {
    }

    private DoctorDTO(long id, String name, String surname, String specialty) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.specialty = specialty;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<DoctorDTO> getListDoctorDTO(List<User> users) {
        return users.stream()
                .map(user -> new DoctorDTO(user.getId(), user.getName(), user.getSurname(), user.getSpecialties().getName()))
                .collect(Collectors.toList());
    }
}
