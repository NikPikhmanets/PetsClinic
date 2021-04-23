package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @EntityGraph(attributePaths = {"specialties"})
    List<User> findAll();
}
