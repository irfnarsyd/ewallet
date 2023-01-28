package com.example.springtest.dto;

import com.example.springtest.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO{

        private Long id;
        private Users users;
        private String username;
        private String destinationUsername;
        private long amount;
        private String status;
    }


