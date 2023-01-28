package com.example.springtest.service;

import com.example.springtest.dto.GetReportDTO;
import com.example.springtest.dto.GetReportObjectDTO;

import com.example.springtest.dto.TransactionResponseDTO;
import com.example.springtest.model.Transaction;
import com.example.springtest.model.Users;
import com.example.springtest.repository.TransactionRepo;
import com.example.springtest.repository.UsersRepo;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportService {

    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UsersRepo usersRepo;

    public List<GetReportDTO> getReportDTO(LocalDate localDate) {
        List<GetReportDTO> report = new ArrayList<>();
//        List<Transaction> transactions = transactionRepo.findAllByDate(localDate);
        return null;
    }
}



