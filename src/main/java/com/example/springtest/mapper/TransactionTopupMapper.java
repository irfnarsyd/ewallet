package com.example.springtest.mapper;

import com.example.springtest.dto.TransactionTopupDTO;
import com.example.springtest.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {}, imports = {})
public interface TransactionTopupMapper extends EntityMapper<TransactionTopupDTO, Transaction> {
}
