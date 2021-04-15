package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.pet.dto.PetFormDTO;
import com.defaultvalue.petsclinic.pet.dto.PetInfoDTO;
import com.defaultvalue.petsclinic.pet.entity.Pet;
import com.defaultvalue.petsclinic.pet.kind.KindOfPet;
import com.defaultvalue.petsclinic.pet.kind.KindOfPetRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured("ROLE_USER")
@RequestMapping("/pets")
public class PetController {

    private final static int REQUEST_PAGE_SIZE = 10;

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
    public void addPet(PetFormDTO petFormDTO) {
        petService.savePet(petFormDTO.getName(), petFormDTO.getKindId());
    }

    @GetMapping("/{id}")
    public PetInfoDTO getPetById(@PathVariable Long id) throws NotFoundException {
        Pet pet = petService.getPetById(id);
        return new PetInfoDTO(pet);
    }

    @GetMapping
    public List<Pet> getAllPetsByUser(@RequestParam(required = false, defaultValue = "0") int page,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return petRepository.findAllByUserId(userDetails.getId(), PageRequest.of(page, REQUEST_PAGE_SIZE));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        petRepository.deleteById(id);
    }
}
