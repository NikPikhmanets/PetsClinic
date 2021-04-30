package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.pet.dto.ImmutablePetShortInfoDTO;
import com.defaultvalue.petsclinic.pet.dto.PetShortInfoDTO;
import com.defaultvalue.petsclinic.pet.entity.Pet;
import com.defaultvalue.petsclinic.pet.kind.KindOfPet;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public PetShortInfoDTO getPetById(Long id) throws NotFoundException {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Pet with %d ID not fount", id)));

        return ImmutablePetShortInfoDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .kind(pet.getKindOfPet().getName())
                .build();
    }

    @Override
    public List<PetShortInfoDTO> getPetsByUser(long userId) {
        List<Pet> pets = petRepository.findAllByUserId(userId);

        return pets.stream().map(pet -> ImmutablePetShortInfoDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .kind(pet.getKindOfPet().getName())
                .build()).collect(Collectors.toList());
    }
}
