package uz.learn.it.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientRegistrationRequestDTO {
    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be blank")
    @NotEmpty(message = "First name can not be empty")
    @Size(min = 3, max = 15, message = "First name should be between 3 and 15 character length!")
    private String firstName;

    @NotNull(message = "Last name can not be null")
    @NotBlank(message = "Last name can not be blank")
    @NotEmpty(message = "Last name can not be empty")
    @Size(min = 3, max = 15, message = "Last name should be between 3 and 15 character length!")
    private String lastName;

    @Pattern(regexp = "^[A-Z]{2}\\d{7}$", message = "Passport info should be in this format: AA1234567")
    private String passportInfo;

    @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number should start with +998 and contain 13 digits")
    private String phoneNumber;

    @Pattern(regexp = "^CLIENT|MANAGER$", message = "Roles' list: MANAGER, CLIENT")
    private String role;
}
