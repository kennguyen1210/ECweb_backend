package ra.academy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id",length = 100)
    private Long productId;
    @Column(length = 100, nullable = false)
    private String sku = UUID.randomUUID().toString();
    @Column(length = 100,name = "product_name")
    private String productName;
    @Column(columnDefinition = "text")
    private String description;
    @Column(name = "unit_price",precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    private String image;
    @Temporal(TemporalType.DATE)
    private Date create_at;
    @Temporal(TemporalType.DATE)
    private Date update_at;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"description","status"})
    private Category category;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<WishList> wishLists;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ShoppingCart> shoppingCarts;

}
