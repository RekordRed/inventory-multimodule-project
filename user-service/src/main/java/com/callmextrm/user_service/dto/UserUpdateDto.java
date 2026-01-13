package com.callmextrm.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(
        @NotBlank String username
) {
}
