package com.callmextrm.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDto(
        @NotBlank String username,
        @NotBlank String password
) {
}
