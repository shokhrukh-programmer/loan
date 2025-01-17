package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredentials {
    private int id;
    private String username;
    private String password;
    private int clientId;
}
