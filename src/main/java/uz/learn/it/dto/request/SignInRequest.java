package uz.learn.it.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
