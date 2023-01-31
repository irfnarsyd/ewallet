package com.example.springtest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserUpdateKtpDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String ktp;
}
