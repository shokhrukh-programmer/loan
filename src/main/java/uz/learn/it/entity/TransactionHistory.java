package uz.learn.it.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "transaction_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String date;

    private String accountNumber;

    private String operation;

    private double remainingBalance;

    private long clientId;
}
