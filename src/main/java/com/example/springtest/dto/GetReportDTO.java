package com.example.springtest.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GetReportDTO {
    private String username;
    private String changeInPercentage;
    private String balanceChangeDate;
}
