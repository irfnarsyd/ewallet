package com.example.springtest.repository;

import com.example.springtest.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Users findByUsername (String username);
    Users findByKtp (String ktp);
}
