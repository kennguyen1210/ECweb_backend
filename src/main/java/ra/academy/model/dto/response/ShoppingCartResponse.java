package ra.academy.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShoppingCartResponse {
    private Integer id;
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;
}
