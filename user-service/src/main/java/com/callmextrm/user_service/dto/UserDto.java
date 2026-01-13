package com.callmextrm.user_service.dto;

import java.util.Set;

public record UserDto(
        Long id,
        String username,
        Set<String> roles

) {
}
