package com.milmertka.beesocial.group;

import javax.validation.constraints.NotBlank;

public record GroupRequest(@NotBlank String name,
                           @NotBlank String description) {
}
