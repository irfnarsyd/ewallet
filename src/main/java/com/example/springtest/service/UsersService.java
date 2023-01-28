package com.example.springtest.service;

import com.example.springtest.constant.Constant;
import com.example.springtest.dto.UserChangePasswordDTO;
import com.example.springtest.dto.UserDTO;
import com.example.springtest.dto.UserRegistrationDTO;
import com.example.springtest.dto.UserUpdateKtpDTO;
import com.example.springtest.mapper.UserMapper;
import com.example.springtest.mapper.UserUpdateKtpMapper;
import com.example.springtest.mapper.UserUpdatePasswordMapper;
import com.example.springtest.model.Users;
import com.example.springtest.repository.TransactionRepo;
import com.example.springtest.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserUpdateKtpMapper userUpdateKtpMapper;
    @Autowired
    private UserUpdatePasswordMapper userUpdatePasswordMapper;

    public UserRegistrationDTO create(UserRegistrationDTO userRegistrationDTO) {
        Users users = userMapper.toEntity(userRegistrationDTO);
        users = usersRepo.save(users);
        return userMapper.toDto(users);
    }

    public boolean validatePassword(String password){
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=]).{10,}";
        return password.matches(pattern);
    }

    public Users findByUsername(String username) {
        return usersRepo.findByUsername(username);
    }

    public UserUpdateKtpDTO updateKtp(String username, UserUpdateKtpDTO userUpdateKtpDTO) {

       Users users = findByUsername(username);
       users.setKtp(userUpdateKtpDTO.getKtp());
       users.setTransactionLimit(Constant.MAX_TRANSACTION_AMOUNT_WITH_KTP);
       users = usersRepo.save(users);

       return userUpdateKtpMapper.toDto(users) ;
    }

    public Users findByKtp(String ktp){
        return usersRepo.findByKtp(ktp);
    }

    public UserChangePasswordDTO updatePassword(UserChangePasswordDTO userChangePasswordDTO) {
        Users users = findByUsername(userChangePasswordDTO.getUsername());
        users.setPassword(userChangePasswordDTO.getNewPassword());
        users = usersRepo.save(users);
        return userUpdatePasswordMapper.toDto(users);
    }

    public boolean getPassword(String username, String password){
        Users users = findByUsername(username);
        return users.getPassword().equals(password);
    }

    public Users updateUnban(String username) {
        Users users = usersRepo.findByUsername(username);
        users.setBanStatus(false);
        return usersRepo.save(users);
    }

    public long getBalance(String username){
        Users users = usersRepo.findByUsername(username);
        return users.getBalance();
    }

    public boolean getPasswordCounter(String username, String password){
        Users users = findByUsername(username);

        if (getBanStatus(username) == false) {
            if (!users.getPassword().equals(password)) {
                users.setPasswordRetry(users.getPasswordRetry() + 1);
                if (users.getPasswordRetry() > 3) {
                    users.setBanStatus(true);
                    users.setPasswordRetry(0);
                }
                usersRepo.save(users);
                return false;
            }
        }
        users.setPasswordRetry(0);
        usersRepo.save(users);
        return true;
    }

    public boolean getBanStatus(String username){
        Users users = usersRepo.findByUsername(username);
        return users.isBanStatus();
    }

    public long getTransactionLimit(String username){
        Users users = usersRepo.findByUsername(username);
        return users.getTransactionLimit();
    }


}

