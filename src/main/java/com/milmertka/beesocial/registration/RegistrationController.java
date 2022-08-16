package com.milmertka.beesocial.registration;

import com.milmertka.beesocial.user.User;
import com.milmertka.beesocial.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
    private static final String REGISTRATION_SUCCESS_MESSAGE = "User successfully registered";

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> signUpUser(@RequestBody User user) {
        userService.signUpUser(user);
        return new ResponseEntity<>(REGISTRATION_SUCCESS_MESSAGE, HttpStatus.CREATED);
    }
}
