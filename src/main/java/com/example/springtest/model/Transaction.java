package com.example.springtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
    @Column(name = "username")
    private String username;
    @Column(name = "amount")
    private long amount;
    @Column(name = "status")
    private String status;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "balanceAfter")
    private long balanceAfter;
    @Column(name = "balanceBefore")
    private long balanceBefore;
    @Column(name = "type")
    private String type;
}
