package ra.academy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.academy.model.dto.request.auth.SignUpRequest;
import ra.academy.model.dto.request.category.CategoryRequest;
import ra.academy.model.dto.request.product.ProductRequest;
import ra.academy.model.dto.request.product.ProductUpdateRequest;
import ra.academy.model.dto.response.DataResponse;
import ra.academy.model.dto.response.MesResponse;
import ra.academy.model.dto.response.PageDataResponse;
import ra.academy.model.dto.response.ProductResponse;
import ra.academy.model.entity.Category;
import ra.academy.model.entity.Product;
import ra.academy.model.entity.Role;
import ra.academy.model.entity.User;
import ra.academy.service.ICategoryService;
import ra.academy.service.IProductService;
import ra.academy.service.IUserService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@EnableSpringDataWebSupport
@RequestMapping("/api.myservice.com/v1/admin")
public class AdminController {
    private final IProductService productService;
    private final IUserService userService;
    private final ICategoryService categoryService;
    // ================= product
    // them moi san pham
    @PostMapping("/products")
    public ResponseEntity<DataResponse<ProductResponse>> addPro(@Valid @ModelAttribute ProductRequest productRequest){
        DataResponse<ProductResponse> p = new DataResponse<>(new ArrayList<>());
        p.getData().add(productService.addPro(productRequest));
        return ResponseEntity.ok(p);
    }
    // chinh sua thong tin san pham
    @PutMapping("/products/{productId}")
    public ResponseEntity<DataResponse<ProductResponse>> editPro(@Valid @ModelAttribute ProductUpdateRequest updateRequest, @PathVariable Long productId){
        updateRequest.setProductId(productId);
        DataResponse<ProductResponse> p = new DataResponse<>(new ArrayList<>());
        p.getData().add(productService.editPro(updateRequest));
        return ResponseEntity.ok(p);
    }
    // lay ve thong tin product theo id
    @GetMapping("/products/{productId}")
    public ResponseEntity<DataResponse<Product>> productFindById(@PathVariable Long productId){
        DataResponse<Product> p = new DataResponse<>(new ArrayList<>());
        p.getData().add(productService.findById(productId));
        return ResponseEntity.ok(p);
    }
    // xoa san pham
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<MesResponse> deletePro(@PathVariable Long productId){
        productService.detele(productId);
        return new ResponseEntity<>(new MesResponse("delete successful !"), HttpStatus.OK);
    }
    // lay san pham ban chay trong thang
    @GetMapping("/dash-board/sales/best-seller-products")
    public ResponseEntity<DataResponse<Product>> getBestSellerByMonth(){
        return ResponseEntity.ok(new DataResponse<>(productService.getBestSellerByMonth()));
    }
    // =====================

    // =========== user
    // lay danh sach user
    @GetMapping("/users")
    public ResponseEntity<PageDataResponse<User>> getAllUser(Pageable pageable){
        return ResponseEntity.ok(userService.findAllHavePageable(pageable));
    }
    // them role cho userorder_detail
    @PostMapping("/users/{userId}/role")
    public ResponseEntity<MesResponse> addRole(@PathVariable String userId, @RequestBody Role role){
        userService.addRole(userId, role.getRoleId());
        return new ResponseEntity<>(new MesResponse("Add role success!"),HttpStatus.OK);
    }
    // xoa role khoi user
    @DeleteMapping("/users/{userId}/role")
    public ResponseEntity<MesResponse> deleteRole(@PathVariable String userId, @RequestBody Role role){
        userService.deleteRole(userId, role.getRoleId());
        return ResponseEntity.ok(new MesResponse("Delete role success!"));
    }
    // lock/unlock user
    @PutMapping("/users/{userId}")
    public ResponseEntity<MesResponse> changeStatus(@PathVariable String userId){
        userService.changeStatus(userId);
        return  ResponseEntity.ok(new MesResponse("Change status success!"));
    }
    // lay ve danh sach role
    @GetMapping("/roles")
    public ResponseEntity<DataResponse<Role>> getAllRole(){
        return ResponseEntity.ok(userService.getListRole());
    }
    // tim kiem nguoi dung theo ten
    @GetMapping("/users/search")
    public ResponseEntity<DataResponse<User>> getUserBySearch(@RequestParam String searchName){
        return ResponseEntity.ok(userService.findUserBySearch(searchName));
    }
    // =================================

    // ======== category
    //lay ve danh sach category
    @GetMapping("/categories")
    public ResponseEntity<PageDataResponse<Category>> getAllCategory(Pageable pageable){
        return ResponseEntity.ok(categoryService.findAllHavePageable(pageable));
    }
    //lay ve category theo id
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<DataResponse<Category>> getCategoryById(@PathVariable Long categoryId){
        DataResponse<Category> p = new DataResponse<>(new ArrayList<>());
        p.getData().add(categoryService.findById(categoryId));
        return ResponseEntity.ok(p);
    }
    // them moi category
    @PostMapping("/categories")
    public ResponseEntity<DataResponse<Category>> addNewCategory(@RequestBody Category category){
        category.setStatus(true);
        DataResponse<Category> p = new DataResponse<>(new ArrayList<>());
        p.getData().add(categoryService.save(category));
        return ResponseEntity.ok(p);
    }
    // update category
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<DataResponse<Category>> updateCategory(@PathVariable Long categoryId,@RequestBody Category category){
        category.setCategoryId(categoryId);
        DataResponse<Category> p = new DataResponse<>(new ArrayList<>());
        p.getData().add(categoryService.save(category));
        return ResponseEntity.ok(p);
    }
    // xoa category
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<MesResponse> deleteCategory(@PathVariable Long categoryId){
        categoryService.detele(categoryId);
        return new ResponseEntity<>(new MesResponse("Delete category success!"),HttpStatus.OK);
    }
    //=======================


}
