package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.kind.KindOfPetRepository;
import com.defaultvalue.petsclinic.pet.kind.KindsOfPet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceIml implements PetService {

    private final KindOfPetRepository kindOfPetRepository;

    @Autowired
    public PetServiceIml(KindOfPetRepository kindOfPetRepository) {
        this.kindOfPetRepository = kindOfPetRepository;
    }

    @Override
    public List<KindsOfPet> getKindsOfPet() {
        return kindOfPetRepository.findAll();
    }
}
