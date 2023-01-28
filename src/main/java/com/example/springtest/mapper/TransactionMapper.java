package com.example.springtest.mapper;

import com.example.springtest.dto.CreateTransactionDTO;
import com.example.springtest.model.Transaction;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {}, imports = {})
public interface TransactionMapper extends EntityMapper<CreateTransactionDTO, Transaction>{

}
