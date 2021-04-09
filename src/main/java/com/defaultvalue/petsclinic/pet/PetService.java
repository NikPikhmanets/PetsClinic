package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.entity.Pet;

import java.util.List;

public interface PetService {
    List<Pet> savePet(Pet pet, long userId);

    Pet getPetById(Long id);
}
