package com.example.springtest.dto;

import com.example.springtest.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class UserChangePasswordDTO {
        private String username;
        private String oldPassword;
        private String newPassword;
    }
