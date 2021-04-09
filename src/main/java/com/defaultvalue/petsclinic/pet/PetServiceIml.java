package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.entity.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceIml implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceIml(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<Pet> savePet(Pet pet, long userId) {
        pet.setUserId(userId);
        petRepository.save(pet);

        return petRepository.findAllByUserId(userId);
    }

    @Override
    public Pet getPetById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        return optionalPet.get(); // TODO
    }
}
