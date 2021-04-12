package com.defaultvalue.petsclinic.pet.dto;

public class PetFormDTO {
    private String name;
    private long kindId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getKindId() {
        return kindId;
    }

    public void setKindId(long kindId) {
        this.kindId = kindId;
    }
}
