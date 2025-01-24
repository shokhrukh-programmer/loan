package uz.learn.it.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Daily_loan_debts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyLoanPaymentDebt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String date;

    private double dailyInterestAmount;

    private long loanId;
}