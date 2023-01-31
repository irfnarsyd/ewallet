package com.example.springtest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
    public class UserChangePasswordDTO {

        @NotBlank
        private String username;
        @NotBlank
        private String oldPassword;
        @NotBlank
        private String newPassword;
    }
