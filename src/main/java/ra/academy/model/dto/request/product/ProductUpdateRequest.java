package ra.academy.model.dto.request.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ra.academy.validate.product.ProductNameUnique;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductUpdateRequest {
    private Long productId;
    @NotBlank(message = "productName is required.")
    @Size(max = 100)
    private String productName;
    @NotBlank(message = "description is required.")
    private String description;
    @NotNull(message = "unitPrice is required.")
    @Positive
    private BigDecimal unitPrice;
    @NotNull(message = "stockQuantity is required.")
    @PositiveOrZero
    private Integer stockQuantity;
    @NotBlank(message = "oldImage is required.")
    private String image;
    private MultipartFile imageUrl;
    @NotNull(message = "category is required.")
    private Long categoryId;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    private Date createAt;
}
