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

@Service
public class TransactionService {


    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionTopupMapper transactionTopupMapper;

    @Autowired
    private TransactionResponseMapper transactionResponseMapper;


    LocalDate localDate = LocalDate.now();

    public TransactionTopupDTO topUp(TransactionTopupDTO transactionTopupDTO){

        Users users = usersRepo.findByUsername(transactionTopupDTO.getUsername());
        Transaction transaction = transactionTopupMapper.toEntity(transactionTopupDTO);
        users.setBalance(users.getBalance() + transactionTopupDTO.getAmount());
        transaction.setUsername(transactionTopupDTO.getUsername());
        transaction.setAmount(transactionTopupDTO.getAmount());
        transaction.setUsers(users);
        transaction.setBalanceBefore(users.getBalance() - transactionTopupDTO.getAmount());
        transaction.setBalanceAfter(users.getBalance());
        transaction.setType(Constant.TOPUP);
        transaction.setDate(localDate);
        transaction = transactionRepo.save(transaction);

        return transactionTopupMapper.toDto(transaction);
    }

    public TransactionResponseDTO create(CreateTransactionDTO createTransactionDTO){

        long tax = Math.round(createTransactionDTO.getAmount() * Constant.TRANSACTION_TAX);

        Users senderUsers = usersRepo.findByUsername(createTransactionDTO.getUsername());
        Users recipientUsers = usersRepo.findByUsername(createTransactionDTO.getDestinationUsername());

        Transaction transactionSender = new Transaction();
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

        transactionRepo.save(transactionSender);
        transactionRepo.save(transactionRecipient);

        return transactionResponseMapper.toDto(transactionSender);
    }


}
