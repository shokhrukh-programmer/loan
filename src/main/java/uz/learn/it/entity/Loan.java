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
@Table(name = "loans")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Loan{");
        sb.append("id=").append(id);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", amount=").append(amount);
        sb.append(", term=").append(term);
        sb.append(", interestRate=").append(interestRate);
        sb.append(", balance=").append(balance);
        sb.append(", debt=").append(debt);
        sb.append(", clientId=").append(client.getId());
        sb.append('}');
        return sb.toString();
    }
}
