package com.milmertka.beesocial.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        // given
        User user = new User("test@beesocial.com", "John", "Doe", "password123");
        userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }
}