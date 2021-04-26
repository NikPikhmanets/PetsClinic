package com.defaultvalue.petsclinic.user.specialty;

import com.defaultvalue.petsclinic.user.Specialties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialties, Long> {
}
