package com.defaultvalue.petsclinic.user.specialty;

import com.defaultvalue.petsclinic.user.Specialties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("specialties")
@Secured("ROLE_ADMIN")
public class SpecialtyController {

    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyController(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping
    public List<Specialties> specialties() {
        return specialtyRepository.findAll();
    }
}
