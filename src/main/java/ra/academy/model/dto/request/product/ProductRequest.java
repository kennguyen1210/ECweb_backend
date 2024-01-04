package ra.academy.model.dto.request.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import ra.academy.validate.product.ImageUrlRequired;
import ra.academy.validate.product.ProductNameUnique;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {
    @NotBlank(message = "productName is required.")
    @Size(max = 100)
    @ProductNameUnique
    private String productName;
    @NotBlank(message = "description is required.")
    private String description;
    @NotNull(message = "unitPrice is required.")
    @Positive
    private BigDecimal unitPrice;
    @NotNull(message = "stockQuantity is required.")
    @PositiveOrZero
    private Integer stockQuantity;
    @NotNull(message = "imageUrl is required.")
    @ImageUrlRequired
    private MultipartFile imageUrl;
    @NotNull(message = "category is required.")
    private Long categoryId;
}
