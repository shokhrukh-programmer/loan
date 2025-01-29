package uz.learn.it.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserCredentials {
    private long id;

    private String username;

    private String password;

    private long clientId;
}
