package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequest {
    @NotNull(message = "Username can not be null!")
    @NotBlank(message = "Username can not be blank!")
    @NotEmpty(message = "Username can not be empty!")
    private String username;

    @NotNull(message = "Password can not be null!")
    @NotBlank(message = "Password can not be blank!")
    @NotEmpty(message = "Password can not be empty!")
    private String password;
}
