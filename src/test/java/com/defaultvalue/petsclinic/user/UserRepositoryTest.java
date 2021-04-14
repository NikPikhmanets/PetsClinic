package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    private String email;
    private String notExistingEmail;
    private User user;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        email = "test-email";
        notExistingEmail = "no-exist";

        user = new User();
        user.setEmail(email);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    void whenFindByEmail_thenReturnUser() {
        User found = userRepository.findByEmail(email);
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void whenFindByEmail_thenReturnNull() {
        User found = userRepository.findByEmail(notExistingEmail);
        assertThat(found).isNull();
    }
}