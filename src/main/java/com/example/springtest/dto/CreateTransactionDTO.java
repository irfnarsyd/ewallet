package com.example.springtest.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
    public class CreateTransactionDTO {

        private long id;
        private String username;
        private String password;
        private String destinationUsername;
        private long amount;
        private String status;

    }


