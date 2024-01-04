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
public class HistoryResponse {
    private String serialNumber;
    private BigDecimal totalPrice;
    private String status;
    private String note;
    private String createAt;
}
