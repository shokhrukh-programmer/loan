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
@Table(name = "transaction_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String accountNumber;

    private String operation;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double remainingBalance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionHistory{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", accountNumber='").append(accountNumber).append('\'');
        sb.append(", operation='").append(operation).append('\'');
        sb.append(", remainingBalance=").append(remainingBalance);
        sb.append(", clientId=").append(client.getId());
        sb.append('}');
        return sb.toString();
    }
}
