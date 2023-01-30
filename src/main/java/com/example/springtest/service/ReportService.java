package com.example.springtest.service;

import com.example.springtest.dto.GetReportDTO;
import com.example.springtest.dto.GetReportObjectDTO;

import com.example.springtest.dto.TransactionResponseDTO;
import com.example.springtest.model.Transaction;
import com.example.springtest.model.Users;
import com.example.springtest.repository.TransactionRepo;
import com.example.springtest.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UsersRepo usersRepo;

    public List<GetReportDTO> getReport(LocalDate date) {
        Iterable<Users> data = usersRepo.findAll();
        List<GetReportDTO> reportDTOS = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM YYYY");


        data.forEach(users -> {
            List<Transaction> transactions = users.getTransactions().stream().filter(transaction -> transaction.getDate().equals(date)).toList();
            String changeInPercentage = "";

            if(!transactions.isEmpty()){
                long firstBalance = transactions.get(0).getBalanceBefore();
                long lastBalance = transactions.get(transactions.size() - 1).getBalanceAfter();
                if(firstBalance == 0){
                changeInPercentage = "-";
                }else {
                    double result = lastBalance - firstBalance;
                    DecimalFormat df = new DecimalFormat("###.##");
                    double finalFormatter = (result/firstBalance) * 100;
                    changeInPercentage = df.format(finalFormatter) + "%";
                }
            }else {
                changeInPercentage = "0%";
            }
            reportDTOS.add(
                    new GetReportDTO(users.getUsername(), changeInPercentage, date.format(formatter))
            );
        });

        return reportDTOS;
    }
}



