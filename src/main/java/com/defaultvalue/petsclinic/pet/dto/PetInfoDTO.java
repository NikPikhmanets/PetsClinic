package com.defaultvalue.petsclinic.pet.dto;

import com.defaultvalue.petsclinic.pet.entity.Pet;

public class PetInfoDTO {

    private String name;
    private String kind;

    public PetInfoDTO(Pet pet) {
        this.name = pet.getName();
        this.kind = pet.getKindOfPet().getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
