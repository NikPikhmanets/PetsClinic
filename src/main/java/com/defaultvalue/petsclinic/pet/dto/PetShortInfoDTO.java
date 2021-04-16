package com.defaultvalue.petsclinic.pet.dto;

import org.immutables.value.Value;

@Value.Immutable
public abstract class PetShortInfoDTO {

    public abstract Long getId();

    public abstract String getName();

    public abstract String getKind();
}
