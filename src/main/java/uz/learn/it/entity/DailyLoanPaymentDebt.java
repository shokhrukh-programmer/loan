package uz.learn.it.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.helper.CustomDoubleSerializer;

import javax.persistence.*;

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

    private String date;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double dailyInterestAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private Loan loan;
}
