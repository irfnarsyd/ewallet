package com.example.springtest.dto;

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
