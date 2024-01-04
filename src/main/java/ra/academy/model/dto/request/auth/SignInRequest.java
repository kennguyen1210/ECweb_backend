package ra.academy.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInRequest {
    @NotBlank(message = "The username is required.")
    private String username;
    @NotBlank(message = "The password is required.")
    private String password;
}
