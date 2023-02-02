package com.example.springtest.controller;

import com.example.springtest.dto.*;
import com.example.springtest.model.Users;
import com.example.springtest.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.NumberFormat;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService service;

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "format invalid");
        } else {
            if (service.findByUsername(userRegistrationDTO.getUsername()) != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is taken");
            } else if (service.validatePassword(userRegistrationDTO.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password format invalid");
            }
        }
            service.create(userRegistrationDTO);
    }

    @GetMapping("/{username}/getinfo")
    public UserGetInfoResponseDTO getInfo(@PathVariable String username) {
        Users users = service.findByUsername(username);
        if (users == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
        }
        return UserGetInfoResponseDTO.builder()
                .username(users.getUsername())
                .ktp(users.getKtp())
                .build();
    }

    @PutMapping("/{username}/addktp")
    public void updateKtp(@PathVariable String username, @Valid @RequestBody UserUpdateKtpDTO userUpdateKtpDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "format invalid");
        }else {
            if (service.findByUsername(username) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
            } else if (service.findByKtp(userUpdateKtpDTO.getKtp()) != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ktp has been used by other user");
            } else if (!service.validateKtpSize(userUpdateKtpDTO.getKtp())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ktp must have 16 digit");
            }
        }
            service.updateKtp(username, userUpdateKtpDTO);
    }

    @GetMapping("/{username}/getbalance")
    public UserGetBalanceResponseDTO getBalance(@PathVariable String username) {
        Users users = service.findByUsername(username);
        if (users == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
        }
        NumberFormat nf = NumberFormat.getNumberInstance();

        return UserGetBalanceResponseDTO.builder()
                .balance(nf.format(users.getBalance()))
                .transactionLimit(nf.format(users.getTransactionLimit()))
                .build();
    }

    @PostMapping("/changepassword")
    public void changePassword(@Valid @RequestBody UserChangePasswordDTO userChangePasswordDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "format invalid");
        } else {
            if (service.findByUsername(userChangePasswordDTO.getUsername()) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
            } else {
                if (!service.getPassword(userChangePasswordDTO.getUsername(), userChangePasswordDTO.getOldPassword())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your old password is incorrect");
                } else if (service.getPassword(userChangePasswordDTO.getUsername(), userChangePasswordDTO.getNewPassword())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your new password is still the same as old password");
                } else if (service.validatePassword(userChangePasswordDTO.getNewPassword())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "new password format invalid");
                }
            }
        }
                userChangePasswordDTO.setNewPassword(userChangePasswordDTO.getNewPassword());
                service.updatePassword(userChangePasswordDTO);
    }

    @PutMapping("{username}/unban")
    public void unban(@PathVariable String username) {
        if (service.findByUsername(username) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
        }
        service.updateUnban(username);
    }
}
