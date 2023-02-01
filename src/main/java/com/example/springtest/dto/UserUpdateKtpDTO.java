package com.example.springtest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserUpdateKtpDTO {

    private String username;

    @NotBlank
    private String ktp;
}
