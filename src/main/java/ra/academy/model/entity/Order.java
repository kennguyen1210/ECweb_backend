package ra.academy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "serial_number",length = 100)
    private String serialNumber = UUID.randomUUID().toString();
    @Column(name = "user_id")
    private String userId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(length = 100)
    private String note;
    @Column(name = "receive_name",length = 100)
    private String receiveName;
    @Column(name = "receive_address")
    private String receiveAddress;
    @Column(name = "receive_phone",length = 15)
    private String receivePhone;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "received_at")
    private Date receivedAt;
}
