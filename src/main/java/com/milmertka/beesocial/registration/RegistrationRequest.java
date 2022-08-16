package com.milmertka.beesocial.registration;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record RegistrationRequest(@NotBlank String firstName,
                                  @NotBlank String lastName,
                                  @Email String email,
                                  @NotBlank String password) {
}
