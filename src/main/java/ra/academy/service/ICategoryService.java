package ra.academy.service;

import org.springframework.data.domain.Pageable;
import ra.academy.model.dto.response.CategoryResponse;
import ra.academy.model.dto.response.PageDataResponse;
import ra.academy.model.entity.Category;

import java.util.List;

public interface ICategoryService extends IGenericService<Category,Long>{
    List<CategoryResponse> findAllCategory(Boolean status);
    PageDataResponse<Category> findAllHavePageable(Pageable pageable);
}
