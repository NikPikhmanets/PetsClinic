package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByUserId(long id);
}
