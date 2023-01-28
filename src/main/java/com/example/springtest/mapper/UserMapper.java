package com.example.springtest.mapper;

import com.example.springtest.dto.UserRegistrationDTO;
import com.example.springtest.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {}, imports = {})
public interface UserMapper extends  EntityMapper<UserRegistrationDTO, Users> {
}
