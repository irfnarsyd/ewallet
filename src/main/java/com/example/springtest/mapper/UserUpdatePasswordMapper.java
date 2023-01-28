package com.example.springtest.mapper;

import com.example.springtest.dto.UserChangePasswordDTO;
import com.example.springtest.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {}, imports = {})
public interface UserUpdatePasswordMapper extends EntityMapper<UserChangePasswordDTO, Users>{
}
