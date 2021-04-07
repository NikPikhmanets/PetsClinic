package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.kind.KindsOfPet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/kinds")
    @ResponseBody
    public List<KindsOfPet> getKindsOfPetList() {
        return petService.getKindsOfPet();
    }
}
