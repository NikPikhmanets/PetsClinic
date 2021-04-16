package com.defaultvalue.petsclinic.issue.dto;

public class IssueDTO {

    private String description;
    private long petId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }
}
