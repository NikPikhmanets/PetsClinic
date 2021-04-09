package com.defaultvalue.petsclinic.issue;


public class Issue {

    private Long id;
    private int doctorId;
    private int kindOfPetId;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getKindOfPetId() {
        return kindOfPetId;
    }

    public void setKindOfPetId(int kindOfPetId) {
        this.kindOfPetId = kindOfPetId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
