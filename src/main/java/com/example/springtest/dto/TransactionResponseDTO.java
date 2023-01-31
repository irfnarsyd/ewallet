package com.example.springtest.dto;

import lombok.*;

@Builder
@Getter
public class TransactionResponseDTO {

    private long trxId;
    private String originUsername;
    private String destinationUsername;
    private Long amount;
    private String status;

}
