package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.kind.KindsOfPet;

import java.util.List;

public interface PetService {
    List<KindsOfPet> getKindsOfPet();

    List<Pet> addPet(Pet pet);

    Pet getPetById(Long id);
}
