package ra.academy.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.academy.model.dto.request.product.ProductRequest;
import ra.academy.model.dto.request.product.ProductUpdateRequest;
import ra.academy.model.dto.response.PageDataResponse;
import ra.academy.model.dto.response.ProductResponse;
import ra.academy.model.entity.Category;
import ra.academy.model.entity.Product;
import ra.academy.repository.ICategoryRepository;
import ra.academy.repository.IProductRepository;
import ra.academy.service.IProductService;
import ra.academy.service.UploadFileService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UploadFileService uploadFileService;
    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new NoSuchElementException("ProductID not found!"));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void detele(Long id) {
        productRepository.findById(id).orElseThrow(()->new NoSuchElementException("ProductID not found!"));
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> findAllBySearch(String search) {
        List<Product> list = productRepository.findAllByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search,search);
        return list.stream().map(p-> ProductResponse.builder()
                        .id(p.getProductId())
                        .productName(p.getProductName())
                        .description(p.getDescription())
                        .imageUrl(p.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public PageDataResponse<ProductResponse> findAll(Pageable pageable) {
        Page<Product> list = productRepository.findAll(pageable);
        List<ProductResponse> content = list.getContent().stream().map(p-> ProductResponse.builder()
                        .id(p.getProductId())
                        .productName(p.getProductName())
                        .description(p.getDescription())
                        .imageUrl(p.getImage())
                        .build())
                .toList();
        return new PageDataResponse<>(content,list.getTotalPages(),list.getSize(), list.getNumber(), list.getSort());
    }

    @Override
    public ProductResponse findProById(Long id) {
        Product p = productRepository.findById(id).orElseThrow(()->new NoSuchElementException("ProductID not found!"));
        return ProductResponse.builder()
                .id(p.getProductId())
                .productName(p.getProductName())
                .description(p.getDescription())
                .imageUrl(p.getImage())
                .build();
    }

    @Override
    public List<ProductResponse> findByCategory(Category category) {
        List<Product> list;
        try {
             list = productRepository.findAllByCategory(category);
        }catch (Exception e){
            throw new NoSuchElementException("CategoryId not found!");
        }
        if(list.isEmpty()){
            throw new NoSuchElementException("CategoryId not found!");
        }
        return list.stream().map(p-> ProductResponse.builder()
                        .id(p.getProductId())
                        .productName(p.getProductName())
                        .description(p.getDescription())
                        .imageUrl(p.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse addPro(ProductRequest productRequest) {
        Product p = new Product();
        modelMapper.map(productRequest,p);
        String image = "";
        if (productRequest.getImageUrl() != null){
            image = uploadFileService.uploadFileToServer(productRequest.getImageUrl());
        }
        p.setImage(image);
        p.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()-> new NoSuchElementException("Category not found!")));
        p.setCreate_at(new Date());

        Product savePro = productRepository.save(p);
        return ProductResponse.builder()
                .id(savePro.getProductId())
                .productName(savePro.getProductName())
                .description(savePro.getDescription())
                .imageUrl(savePro.getImage())
                .build();
    }

    @Override
    public ProductResponse editPro(ProductUpdateRequest updateRequest) {
        Product p = new Product();
        modelMapper.map(updateRequest,p);
        if (updateRequest.getImageUrl() != null){
            p.setImage(uploadFileService.uploadFileToServer(updateRequest.getImageUrl()));
        }
        p.setCategory(categoryRepository.findById(updateRequest.getCategoryId()).orElseThrow(()-> new NoSuchElementException("Category not found!")));
        p.setUpdate_at(new Date());
        try {
            Product savePro = productRepository.save(p);
            return ProductResponse.builder()
                    .id(savePro.getProductId())
                    .productName(savePro.getProductName())
                    .description(savePro.getDescription())
                    .imageUrl(savePro.getImage())
                    .build();
        }catch (Exception e){
            throw new RuntimeException("ProductName is exist!");
        }


    }

    @Override
    public List<ProductResponse> getByCreateAt() {
        try{
            List<Product> list = productRepository.findAllByCreate_at();
            return list.stream().map(p-> ProductResponse.builder()
                            .id(p.getProductId())
                            .productName(p.getProductName())
                            .description(p.getDescription())
                            .imageUrl(p.getImage())
                            .build())
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Product not found!");
        }

    }

    @Override
    public List<ProductResponse> getFeaturedProducts() {
        List<Product> list;
        List<Product> newList = new ArrayList<>();
        try {
            list = productRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Product not found!");
        }
        for (int i = 0; i < 10; i++ ){
            int index = new Random().nextInt(list.size());
            newList.add(list.remove(index));
        }
        return newList.stream().map(p-> ProductResponse.builder()
                        .id(p.getProductId())
                        .productName(p.getProductName())
                        .description(p.getDescription())
                        .imageUrl(p.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getBestSellerByMonth() {
        List<Product> list = productRepository.findAllBestSellerByMonth();
        return list;
    }
}
