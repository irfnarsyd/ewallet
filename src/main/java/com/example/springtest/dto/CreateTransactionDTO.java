package com.example.springtest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Data
    public class CreateTransactionDTO {

        private long id;

        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String destinationUsername;
        @NotNull
        private Long amount;

        private String status;

    }


