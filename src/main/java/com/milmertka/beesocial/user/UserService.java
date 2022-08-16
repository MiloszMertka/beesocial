package com.milmertka.beesocial.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE = "User with email %s does not exists";
    private static final String EMAIL_IS_TAKEN_MESSAGE = "Email %s is already taken";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }

    public void signUpUser(User user) {
        final String email = user.getEmail();

        if (emailIsTaken(email)) {
            throw new IllegalStateException(String.format(EMAIL_IS_TAKEN_MESSAGE, email));
        }

        // TODO: Encode password

        userRepository.save(user);
    }

    private boolean emailIsTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
