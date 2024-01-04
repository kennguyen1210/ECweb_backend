package ra.academy.model.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ra.academy.validate.product.ImageUrlRequired;
import ra.academy.validate.user.PhoneUnique;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountRequest {

    @NotBlank(message = "The fullName is required.")
    private String fullName;
    @Email(message = "The email must be email format.")
    private String email;
    @Pattern(regexp = "^84[0-9]{9}$", message = "The phone must be match VietNam phone format.")
    private String phone;
    @NotBlank
    private String address;
    private MultipartFile avatar;
}
