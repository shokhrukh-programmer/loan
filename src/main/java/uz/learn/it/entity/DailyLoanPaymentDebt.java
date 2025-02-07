package uz.learn.it.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "daily_loan_payment_debts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyLoanPaymentDebt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    private double dailyInterestAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private Loan loan;
}
