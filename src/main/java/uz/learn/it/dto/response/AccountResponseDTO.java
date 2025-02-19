package uz.learn.it.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.helper.CustomDoubleSerializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDTO {
    private long id;

    private String accountType;

    private String accountNumber;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double balance;

    private long clientId;
}
