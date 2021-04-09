package com.defaultvalue.petsclinic.pet.kind;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindOfPetRepository extends JpaRepository<KindOfPet, Long> {
}
