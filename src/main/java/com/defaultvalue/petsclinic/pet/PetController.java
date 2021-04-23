package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.pet.dto.PetFormDTO;
import com.defaultvalue.petsclinic.pet.dto.PetShortInfoDTO;
import com.defaultvalue.petsclinic.pet.kind.KindOfPet;
import com.defaultvalue.petsclinic.pet.kind.KindOfPetRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Secured("ROLE_USER")
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
    @ResponseBody
    public List<KindOfPet> getKindOfPetList() {
        return kindOfPetRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addPet(PetFormDTO petFormDTO) {
        petService.savePet(petFormDTO.getName(), petFormDTO.getKindId());
    }

    @GetMapping("/{id}")
    public String getViewWithPetById(Model model, @PathVariable Long id) throws NotFoundException {
        PetShortInfoDTO pet = petService.getPetById(id);
        model.addAttribute("pet", pet);

        return "pet";
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    public PetShortInfoDTO getPetById(@PathVariable Long id) throws NotFoundException {
        return petService.getPetById(id);
    }

    @GetMapping
    @ResponseBody
    public List<PetShortInfoDTO> getPetsByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return petService.getPetsByUser(userDetails.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        petRepository.deleteById(id);
    }
}
