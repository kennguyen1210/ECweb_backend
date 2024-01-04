package ra.academy.model.dto.request.shoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingCartRequest {
    private Long productId;
    private Integer quantity;
}
