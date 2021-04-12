package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.entity.Pet;
import javassist.NotFoundException;

public interface PetService {
    void savePet(String nameOfPet, long kindId);

    Pet getPetById(Long id) throws NotFoundException;
}
