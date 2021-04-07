package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.kind.KindsOfPet;

import java.util.List;

public interface PetService {
    List<KindsOfPet> getKindsOfPet();
}
