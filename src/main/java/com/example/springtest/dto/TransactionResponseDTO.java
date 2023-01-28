package com.example.springtest.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionResponseDTO {

    private long trxId;
    private String originUsername;
    private String destinationUsername;
    private long amount;
    private String status;
}
