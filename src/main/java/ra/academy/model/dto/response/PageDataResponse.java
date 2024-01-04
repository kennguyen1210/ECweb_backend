package ra.academy.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageDataResponse<T> {
    private List<T> content;
    private int totalPages;
    private int size;
    private int number;
    private Sort sort;
}
