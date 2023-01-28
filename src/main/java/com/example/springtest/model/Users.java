package com.example.springtest.model;

import com.example.springtest.constant.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
    public class Users extends AbstractAuditingEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "username", unique = true)
        private String username;

        @Column(name = "password")

        private String password;
        @Column(name = "no_ktp")
        private String ktp;
        @Column(name = "banStatus")
        private boolean banStatus;
        @Column(name = "balance")
        private long balance ;
        @Column(name = "transaction_limit")
        private Long transactionLimit = Constant.MAX_TRANSACTION_AMOUNT;
        @Column(name = "password_retry_counter")
        private int passwordRetry ;
    }


