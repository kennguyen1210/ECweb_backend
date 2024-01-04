package ra.academy.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private String username;
    private String fullName;
    private String avatar;
    private String email;
    private String phone;
    private Boolean status;
    private String address;
    private Date createAt;
    private Date updateAt;
    private String role;
}
