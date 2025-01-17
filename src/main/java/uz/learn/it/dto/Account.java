package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int id;
    private String accountType;
    private String accountNumber;
    private double balance;
    private int clientId;
}
