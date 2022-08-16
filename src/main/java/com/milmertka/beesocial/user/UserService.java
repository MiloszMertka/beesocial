package com.milmertka.beesocial.user;

import com.milmertka.beesocial.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE = "User with email %s does not exists";
    private static final String EMAIL_IS_TAKEN_MESSAGE = "Email %s is already taken";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }

    public void signUpUser(RegistrationRequest registrationRequest) {
        final String email = registrationRequest.email();

        if (emailIsTaken(email)) {
            throw new IllegalStateException(String.format(EMAIL_IS_TAKEN_MESSAGE, email));
        }

        User user = createUserFromRegistrationRequest(registrationRequest);
        encodeUserPassword(user);
        userRepository.save(user);
    }

    private boolean emailIsTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private User createUserFromRegistrationRequest(RegistrationRequest registrationRequest) {
        return new User(registrationRequest.email(),
                        registrationRequest.firstName(),
                        registrationRequest.lastName(),
                        registrationRequest.password());
    }

    private void encodeUserPassword(User user) {
        final String password = user.getPassword();
        final String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
    }
}
