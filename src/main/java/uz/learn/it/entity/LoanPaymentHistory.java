package uz.learn.it.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.helper.CustomDoubleSerializer;

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

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double amount;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double interestPayment;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double mainPayment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private Loan loan;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoanPaymentHistory{");
        sb.append("id=").append(id);
        sb.append(", amount=").append(amount);
        sb.append(", interestPayment=").append(interestPayment);
        sb.append(", mainPayment=").append(mainPayment);
        sb.append(", date=").append(date);
        sb.append(", loanId=").append(loan.getId());
        sb.append('}');
        return sb.toString();
    }
}
