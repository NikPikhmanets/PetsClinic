package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.pet.entity.Pet;
import com.defaultvalue.petsclinic.pet.kind.KindOfPet;
import com.defaultvalue.petsclinic.pet.kind.KindOfPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final PetRepository petRepository;
    private final KindOfPetRepository kindOfPetRepository;

    @Autowired
    public PetController(PetService petService, PetRepository petRepository, KindOfPetRepository kindOfPetRepository) {
        this.petService = petService;
        this.petRepository = petRepository;
        this.kindOfPetRepository = kindOfPetRepository;
    }

    @GetMapping("/kinds")
    public List<KindOfPet> getKindOfPetList() {
        return kindOfPetRepository.findAll();
    }

    @PostMapping
    public List<Pet> addPet(Pet pet, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return petService.savePet(pet, userDetails.getId());
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @GetMapping
    public List<Pet> getAllPetsByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return petRepository.findAllByUserId(userDetails.getId());
    }
}
