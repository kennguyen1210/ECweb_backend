package ra.academy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId orderDetailId;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(length = 100)
    private String name;
    @Column(name = "unit_price",precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "order_quantity")
    private Integer orderQuantity;
}
