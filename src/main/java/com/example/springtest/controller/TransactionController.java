package com.example.springtest.controller;

import com.example.springtest.constant.Constant;
import com.example.springtest.dto.CreateTransactionDTO;
import com.example.springtest.dto.TransactionResponseDTO;
import com.example.springtest.dto.TransactionTopupDTO;
import com.example.springtest.service.TransactionService;
import com.example.springtest.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;
    @Autowired
    UsersService usersService;

    @PostMapping("/topup")
    public void topUp(@Valid @RequestBody TransactionTopupDTO transactionTopupDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "format invalid");
        }
        if (usersService.findByUsername(transactionTopupDTO.getUsername()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
        } else if (usersService.getBanStatus(transactionTopupDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is banned");
        } else if (usersService.getPasswordCounter(transactionTopupDTO.getUsername(), transactionTopupDTO.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password invalid");
        } else if (transactionTopupDTO.getAmount() > Constant.MAX_TOPUP) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "max topup exceeded");
        } else if (usersService.getBalance(transactionTopupDTO.getUsername()) + transactionTopupDTO.getAmount() > Constant.MAX_BALANCE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "max balance exceeded");
        }
        service.topUp(transactionTopupDTO);
    }


    @PostMapping("/create")
    public TransactionResponseDTO create(@Valid @RequestBody CreateTransactionDTO createTransactionDTO, BindingResult results) {

        if (results.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "format invalid");
        }else {
            if (usersService.findByUsername(createTransactionDTO.getUsername()) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
            } else if (usersService.getBanStatus(createTransactionDTO.getUsername())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is banned");
            } else if (usersService.findByUsername(createTransactionDTO.getDestinationUsername()) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "destination user not found");
            } else if (usersService.getPasswordCounter(createTransactionDTO.getUsername(), createTransactionDTO.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password invalid");
            } else if (createTransactionDTO.getAmount() > usersService.getTransactionLimit(createTransactionDTO.getUsername())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "transaction limit exceeded");
            } else if (service.getBalanceAfterTax(createTransactionDTO) < Constant.MIN_BALANCE) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not enough balance");
            } else if (createTransactionDTO.getAmount() < Constant.MIN_TRANSACTION_AMOUNT) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "minimum trx amount is " + Constant.MIN_TRANSACTION_AMOUNT);
            }
        }

        TransactionResponseDTO result = service.create(createTransactionDTO);
        return TransactionResponseDTO.builder()
                .trxId(result.getTrxId())
                .originUsername(createTransactionDTO.getUsername())
                .destinationUsername(createTransactionDTO.getDestinationUsername())
                .amount(createTransactionDTO.getAmount())
                .status(result.getStatus())
                .build();
    }
}

