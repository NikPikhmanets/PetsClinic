package com.defaultvalue.petsclinic.pet;

import lombok.Data;

@Data
public class Pet {

    private Long id;
    private String name;
    private int kindId;
    private int userId;
}
