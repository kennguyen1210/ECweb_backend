package ra.academy.model.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.academy.model.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderResponse {
    private String serialNumber;
    private BigDecimal totalPrice;
    private String status;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private String createAt;
    private String receivedAt;
}
