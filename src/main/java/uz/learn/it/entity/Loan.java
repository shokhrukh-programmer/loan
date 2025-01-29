package uz.learn.it.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.helper.CustomDoubleSerializer;

import javax.persistence.*;

@Entity
@Table(name = "loans")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String createdDate;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double amount;

    private int term;

    private double interestRate;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double balance;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double debt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}
