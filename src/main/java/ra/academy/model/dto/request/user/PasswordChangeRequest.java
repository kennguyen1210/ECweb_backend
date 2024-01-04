package ra.academy.model.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.academy.validate.user.PasswordMatching;

@NoArgsConstructor
@AllArgsConstructor
@Data
@PasswordMatching(
        password = "newPass",
        confirmPassword = "confirmNewPass"
)
public class PasswordChangeRequest {
    @NotBlank(message = "The password is required.")
    private String oldPassword;
    @NotBlank(message = "The password is required.")
    private String newPass;
    @NotBlank(message = "The password is required.")
    private String confirmNewPass;
}
