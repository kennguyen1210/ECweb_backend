package ra.academy.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountResponse {
    private String username;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String phone;
    private String address;
}
