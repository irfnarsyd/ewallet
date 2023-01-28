package com.example.springtest.mapper;

import com.example.springtest.dto.UserUpdateKtpDTO;
import com.example.springtest.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {}, imports = {})
public interface UserUpdateKtpMapper extends EntityMapper<UserUpdateKtpDTO, Users>{
}
