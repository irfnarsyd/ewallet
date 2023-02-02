package com.example.springtest.model;

import com.example.springtest.constant.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

        private String password;
        @Column(name = "no_ktp")
        private String ktp;

        private boolean banStatus;

        private long balance ;
        private Long transactionLimit = Constant.MAX_TRANSACTION_AMOUNT;
        @Column(name = "password_retry_counter")
        private int passwordRetry ;

        @OneToMany(mappedBy = "users")
        private List<Transaction> transactions;
    }


