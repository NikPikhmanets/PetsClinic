package com.defaultvalue.petsclinic.user.dto;

import com.defaultvalue.petsclinic.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorDTO {

    private String name;
    private String surname;
    private String specialties;

    public DoctorDTO() {
    }

    public DoctorDTO(String name, String surname, String specialties) {
        this.name = name;
        this.surname = surname;
        this.specialties = specialties;
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

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public List<DoctorDTO> getListDoctorDTO(List<User> users) {
        return users.stream()
                .map(user -> new DoctorDTO(user.getName(), user.getSurname(), user.getSpecialties().getName()))
                .collect(Collectors.toList());
    }
}
