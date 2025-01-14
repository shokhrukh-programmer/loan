package uz.learn.it.dto.request;

import jakarta.validation.constraints.*;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassportInfo() {
        return passportInfo;
    }

    public void setPassportInfo(String passportInfo) {
        this.passportInfo = passportInfo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
