package com.example.springtest.controller;

import com.example.springtest.dto.GetReportDTO;
import com.example.springtest.dto.GetReportObjectDTO;
import com.example.springtest.model.Transaction;
import com.example.springtest.repository.TransactionRepo;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    TransactionRepo transactionRepo;

    @GetMapping("/getreport/{date}")
    public ResponseEntity<Object> getReport(@PathVariable LocalDate date){
        List<Transaction> result= transactionRepo.findAllByDate(date);

        Map<String, List<Transaction>> map = result.stream()
                .collect(Collectors.groupingBy(Transaction::getUsername));

        List<GetReportDTO> reportDTOS = new ArrayList<>();

        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);
//        DateFormat dateFormat = new

        map.forEach((username,transactions)->{
//            System.out.println(username);
//            transactions.forEach(t-> System.out.println("\t" + t.getType() + " " + t.getCreatedDate()));
//            System.out.println();
            long first = transactions.get(0).getBalanceBefore();
            long last = transactions.get(transactions.size()-1).getBalanceAfter();
            double changeInPercentage = 1.0 * (last - first) / first;

            reportDTOS.add(
                    new GetReportDTO(username,numberFormat.format(changeInPercentage), date)
            );
        });

        return new ResponseEntity<>(new GetReportObjectDTO(reportDTOS),HttpStatus.OK);
    }
}
