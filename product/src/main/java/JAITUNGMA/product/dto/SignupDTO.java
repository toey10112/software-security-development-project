package JAITUNGMA.product.dto;

import JAITUNGMA.product.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignupDTO {
    @NotBlank(message = "Username is required")
    @Size(min=4, message = "Username must have at least 4 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min=12, max=128, message = "Password must have at least 12 characters")
    @ValidPassword
    private String password;

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]+$",
            message = "First name can only contain letters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^(ROLE_ADMIN|ROLE_USER)$",
            message = "Role is in an incorrect format.")
    private String role;

    private String recaptcha;
}

