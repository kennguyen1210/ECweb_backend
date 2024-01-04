package ra.academy.service;

import org.springframework.data.domain.Pageable;
import ra.academy.model.dto.request.product.ProductRequest;
import ra.academy.model.dto.request.product.ProductUpdateRequest;
import ra.academy.model.dto.response.PageDataResponse;
import ra.academy.model.dto.response.ProductResponse;
import ra.academy.model.entity.Category;
import ra.academy.model.entity.Product;

import java.util.List;

public interface IProductService extends IGenericService<Product,Long>{
    List<ProductResponse> findAllBySearch(String search);
    PageDataResponse<ProductResponse> findAll(Pageable pageable);
    ProductResponse findProById(Long id);
    List<ProductResponse> findByCategory(Category category);

    ProductResponse addPro(ProductRequest productRequest);
    ProductResponse editPro(ProductUpdateRequest updateRequest);
    List<ProductResponse> getByCreateAt();
    List<ProductResponse> getFeaturedProducts();
    List<Product> getBestSellerByMonth();
}
