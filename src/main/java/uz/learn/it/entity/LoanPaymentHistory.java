package uz.learn.it.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.*;
import uz.learn.it.helper.CustomDoubleSerializer;

import javax.persistence.*;

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

    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    private Loan loan;
}
