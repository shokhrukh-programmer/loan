package uz.learn.it.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.enums.Role;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRequestDTO implements Serializable {
    private String token;
    private Role role;
    private LocalDate date;
    private long clientId;
}
