package com.example.springtest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
public class UserRegistrationDTO {
    private long id;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
