package ra.academy.model.dto.request.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressRequest {
    private String userId;
    @NotBlank(message = "ReceiveName is required")
    private String receiveName;
    @NotBlank(message = "FullAddress is required")
    private String fullAddress;
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^84[0-9]{9}$", message = "The phone must be match VietNam phone format.")
    private String phone;

}
