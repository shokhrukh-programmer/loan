package uz.learn.it.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    public String username;
    private String password;
    private String role;
}
