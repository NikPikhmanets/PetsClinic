package com.defaultvalue.petsclinic.issue;

import lombok.Data;

@Data
public class Issue {

    private Long id;
    private int doctorId;
    private int kindOfPetId;
    private String description;
}
