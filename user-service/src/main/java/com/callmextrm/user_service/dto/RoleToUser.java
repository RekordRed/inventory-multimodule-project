package com.callmextrm.user_service.dto;


import jakarta.validation.constraints.NotBlank;


public record RoleToUser(
        @NotBlank String username,
        @NotBlank String rolename) {

}
