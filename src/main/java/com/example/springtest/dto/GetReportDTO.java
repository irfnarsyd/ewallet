package com.example.springtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@AllArgsConstructor
@Data
public class GetReportDTO {
    private String username;
    private String changeInPercentage;
    private LocalDate balanceChangeDate;
}
