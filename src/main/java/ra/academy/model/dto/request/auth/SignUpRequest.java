package ra.academy.model.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ra.academy.validate.user.PasswordMatching;
import ra.academy.validate.user.PhoneUnique;
import ra.academy.validate.user.UsernameUnique;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword"
)
public class SignUpRequest {
    @NotBlank(message = "The username is required.")
    @Pattern(regexp = "^\\w{6,100}$", message = "The username must be from 6 to 100 characters and does not contain special characters.")
    @UsernameUnique
    private String username;
    @NotBlank(message = "The email is required.")
    @Email(message = "The email must be email format.")
    private String email;
    @NotBlank(message = "The fullName is required.")
    private String fullName;
    @NotBlank(message = "The password is required.")
    private String password;
    @NotBlank(message = "The confirmPassword is required.")
    private String confirmPassword;
    private MultipartFile avatarImg;
    @Pattern(regexp = "^84[0-9]{9}$", message = "The phone must be match VietNam phone format.")
    @PhoneUnique
    private String phone;
    @NotBlank
    private String address;
    private List<String> roleList;
}
