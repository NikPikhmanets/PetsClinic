package com.defaultvalue.petsclinic.pet;

import com.defaultvalue.petsclinic.pet.entity.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @EntityGraph(attributePaths = {"kindOfPet"})
    List<Pet> findAllByUserId(long id);

    @Override
    @EntityGraph(attributePaths = {"kindOfPet", "issues"})
    Optional<Pet> findById(Long id);
}
