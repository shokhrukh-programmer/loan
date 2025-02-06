package uz.learn.it.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.helper.CustomDoubleSerializer;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double dailyInterestAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private Loan loan;
}
