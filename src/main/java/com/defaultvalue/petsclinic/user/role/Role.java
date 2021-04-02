package com.defaultvalue.petsclinic.user.role;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
