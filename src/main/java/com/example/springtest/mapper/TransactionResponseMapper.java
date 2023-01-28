package com.example.springtest.mapper;

import com.example.springtest.dto.CreateTransactionDTO;
import com.example.springtest.dto.TransactionDTO;
import com.example.springtest.dto.TransactionResponseDTO;
import com.example.springtest.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {}, imports = {})
public interface TransactionResponseMapper extends EntityMapper<TransactionResponseDTO, Transaction>{

    @Mapping(target = "trxId", source = "id")
    TransactionResponseDTO toDto(Transaction transaction);

    @Mapping(target = "id", source = "trxId")
    Transaction toEntity(TransactionResponseDTO transactionResponseDTO);

}
