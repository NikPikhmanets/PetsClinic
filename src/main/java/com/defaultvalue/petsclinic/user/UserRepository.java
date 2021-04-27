package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @EntityGraph(value = "User.specialties")
    Page<User> findAllBySpecialtiesIsNotNull(Pageable pageable);

    @EntityGraph(value = "User")
    Page<User> findAllBySpecialtiesIsNull(Pageable pageable);
}
