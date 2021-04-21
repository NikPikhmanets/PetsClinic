package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.dto.PetShortInfoDTO;
import javassist.NotFoundException;

import java.util.List;

public interface PetService {
    void savePet(String nameOfPet, long kindId);

    PetShortInfoDTO getPetById(Long id) throws NotFoundException;

    List<PetShortInfoDTO> getPetsByUser(long userId);
}
