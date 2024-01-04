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
public class Item {
    private String productName;
    private String imageUrl;
    private BigDecimal unitPrice;
    private Integer quantity;
}
