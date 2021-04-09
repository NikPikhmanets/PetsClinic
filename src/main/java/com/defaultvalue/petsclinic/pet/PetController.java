package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.pet.kind.KindsOfPet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/kinds")
    public List<KindsOfPet> getKindsOfPetList() {
        return petService.getKindsOfPet();
    }

    @PostMapping
    public List<Pet> addNewPet(Pet pet, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        pet.setUserId((int) userDetails.getId());
        List<Pet> pets = petService.addPet(pet);
        System.out.println(pets);

        return pets;
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }
}
