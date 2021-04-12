package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.pet.entity.Pet;
import com.defaultvalue.petsclinic.pet.kind.KindOfPet;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PetServiceIml implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceIml(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void savePet(String nameOfPet, long kindId) {
        long userId = getUserId();
        Pet pet = new Pet();
        pet.setName(nameOfPet);
        pet.setUserId(userId);
        KindOfPet kindOfPet = new KindOfPet();
        kindOfPet.setId(kindId);
        pet.setKindOfPet(kindOfPet);
        petRepository.save(pet);
    }

    private long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getId();
    }

    @Override
    public Pet getPetById(Long id) throws NotFoundException {
        return petRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Pet with %d ID not fount", id)));
    }
}
