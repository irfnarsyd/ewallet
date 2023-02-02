package com.example.springtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Transaction")
public class Transaction extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "users_id")
    private Users users;

    private String username;

    private long amount;

    private String status;
    private LocalDate date;

    private long balanceAfter;

    private long balanceBefore;

    private String type;
}
