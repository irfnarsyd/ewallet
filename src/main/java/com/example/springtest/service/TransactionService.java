package com.example.springtest.service;

import com.example.springtest.constant.Constant;
import com.example.springtest.dto.CreateTransactionDTO;
import com.example.springtest.dto.TransactionResponseDTO;
import com.example.springtest.dto.TransactionTopupDTO;
import com.example.springtest.mapper.TransactionMapper;
import com.example.springtest.mapper.TransactionResponseMapper;
import com.example.springtest.mapper.TransactionTopupMapper;
import com.example.springtest.model.Transaction;
import com.example.springtest.model.Users;
import com.example.springtest.repository.TransactionRepo;
import com.example.springtest.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {


    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    TransactionTopupMapper transactionTopupMapper;

    @Autowired
    TransactionResponseMapper transactionResponseMapper;

    @Autowired
    TransactionMapper transactionMapper;

    LocalDate localDate = LocalDate.now();

    public void topUp(TransactionTopupDTO transactionTopupDTO) {

        Users users = usersRepo.findByUsername(transactionTopupDTO.getUsername());
        Transaction transaction = new Transaction();
        users.setBalance(users.getBalance() + transactionTopupDTO.getAmount());
        transaction.setUsername(transactionTopupDTO.getUsername());
        transaction.setAmount(transactionTopupDTO.getAmount());
        transaction.setUsers(users);
        transaction.setBalanceBefore(users.getBalance() - transactionTopupDTO.getAmount());
        transaction.setBalanceAfter(users.getBalance());
        transaction.setType(Constant.TOPUP);
        transaction.setDate(localDate);
        transaction = transactionRepo.save(transaction);

        transactionTopupMapper.toDto(transaction);
    }

    public TransactionResponseDTO create(CreateTransactionDTO createTransactionDTO) {

        long tax = Math.round(createTransactionDTO.getAmount() * Constant.TRANSACTION_TAX);

        Users senderUsers = usersRepo.findByUsername(createTransactionDTO.getUsername());
        Users recipientUsers = usersRepo.findByUsername(createTransactionDTO.getDestinationUsername());


        Transaction transactionSender = transactionMapper.toEntity(createTransactionDTO);
        if (getBalanceAfterTax(createTransactionDTO) > Constant.MIN_BALANCE) {
            transactionSender = new Transaction();
            transactionSender.setUsername(createTransactionDTO.getUsername());
            transactionSender.setAmount(createTransactionDTO.getAmount());
            transactionSender.setBalanceBefore(senderUsers.getBalance());
            transactionSender.setBalanceAfter(senderUsers.getBalance() - createTransactionDTO.getAmount() - tax);
            transactionSender.setStatus("SETTLED");
            transactionSender.setType(Constant.SENDER);
            transactionSender.setUsers(senderUsers);
            transactionSender.setDate(localDate);


            Transaction transactionRecipient = new Transaction();
            transactionRecipient.setUsername(createTransactionDTO.getDestinationUsername());
            transactionRecipient.setAmount(createTransactionDTO.getAmount());
            transactionRecipient.setBalanceBefore(recipientUsers.getBalance());
            transactionRecipient.setBalanceAfter(recipientUsers.getBalance() + createTransactionDTO.getAmount());
            transactionRecipient.setStatus("SETTLED");
            transactionRecipient.setType(Constant.RECIPIENT);
            transactionRecipient.setUsers(recipientUsers);
            transactionRecipient.setDate(localDate);

            senderUsers.setBalance(senderUsers.getBalance() - createTransactionDTO.getAmount() - tax);
            recipientUsers.setBalance(recipientUsers.getBalance() + createTransactionDTO.getAmount());

            transactionRepo.saveAll(List.of(transactionSender, transactionRecipient));

        }
        return transactionResponseMapper.toDto(transactionSender);

    }

    public double getBalanceAfterTax(CreateTransactionDTO createTransactionDTO) {
        Users users = usersRepo.findByUsername(createTransactionDTO.getUsername());
        long senderBalance = users.getBalance();
        long senderBalanceAfterTransferOut = senderBalance - createTransactionDTO.getAmount();
        double taxAmount = createTransactionDTO.getAmount() * Constant.TRANSACTION_TAX;

        return senderBalanceAfterTransferOut - taxAmount;
    }
}
