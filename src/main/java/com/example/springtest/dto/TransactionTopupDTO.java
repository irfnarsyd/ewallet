package com.example.springtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionTopupDTO {
    private long id;
    private String username;
    private String password;
    private long amount;
}
