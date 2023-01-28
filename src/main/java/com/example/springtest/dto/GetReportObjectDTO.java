package com.example.springtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class GetReportObjectDTO {
    private List<GetReportDTO> reportBalanceChangeInPercentage;
}
