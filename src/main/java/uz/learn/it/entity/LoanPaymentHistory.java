package uz.learn.it.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "loan_payment_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanPaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double amount;

    private double interestPayment;

    private double mainPayment;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private Loan loan;
}
