package com.example.springtest.dto;

import com.example.springtest.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

        private Long id;
        private String username;
        private String password;
        private String ktp;
        private boolean banStatus;
        private long balance ;
        private long transactionLimit = Constant.MAX_TRANSACTION_AMOUNT;
    }

