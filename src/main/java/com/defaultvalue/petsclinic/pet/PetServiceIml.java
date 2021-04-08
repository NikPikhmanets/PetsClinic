package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.kind.KindOfPetRepository;
import com.defaultvalue.petsclinic.pet.kind.KindsOfPet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceIml implements PetService {

    private final KindOfPetRepository kindOfPetRepository;
    private final List<Pet> pets;

    @Autowired
    public PetServiceIml(KindOfPetRepository kindOfPetRepository) {
        this.kindOfPetRepository = kindOfPetRepository;

        pets = new ArrayList<>();
    }

    @Override
    public List<KindsOfPet> getKindsOfPet() {
        return kindOfPetRepository.findAll();
    }

    @Override
    public List<Pet> addPet(Pet pet) {
        pets.add(pet);

        return pets;
    }

    @Override
    public Pet getPetById(Long id) {
        return pets.get(Math.toIntExact(id));
    }
}
