package com.milmertka.beesocial.user;

import com.milmertka.beesocial.registration.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void itShouldLoadUserByUsername() {
        // given
        String username = "test@beesocial.com";
        given(userRepository.findByEmail(username)).willReturn(Optional.of(new User()));

        // when
        userService.loadUserByUsername(username);

        // then
        verify(userRepository).findByEmail(username);
    }

    @Test
    void itShouldThrowUsernameNotFoundExceptionWhenUserIsNotFound() {
        // given
        String username = "test@beesocial.com";
        given(userRepository.findByEmail(username)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userService.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with email " + username + " does not exists");
    }

    @Test
    void itShouldSignUpUser() {
        // given
        RegistrationRequest registrationRequest = new RegistrationRequest("John", "Doe",
                "test@beesocial.com", "password123");
        User user = new User(registrationRequest.email(), registrationRequest.firstName(), registrationRequest.lastName(), registrationRequest.password());
        given(passwordEncoder.encode(registrationRequest.password())).willReturn(registrationRequest.password());
        given(userRepository.findByEmail(registrationRequest.email())).willReturn(Optional.empty());

        // when
        userService.signUpUser(registrationRequest);

        // then
        verify(userRepository).save(user);
    }

    @Test
    void itShouldThrowExceptionWhenEmailIsTaken() {
        // given
        RegistrationRequest registrationRequest = new RegistrationRequest("John", "Doe",
                "test@beesocial.com", "password123");
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(new User()));

        // when
        // then
        assertThatThrownBy(() -> userService.signUpUser(registrationRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Email " + registrationRequest.email() + " is already taken");
    }
}