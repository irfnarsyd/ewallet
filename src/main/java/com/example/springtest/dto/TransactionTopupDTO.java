package com.example.springtest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter

public class TransactionTopupDTO {
    private long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull(message = "format invalid")
    private Long amount;
}
