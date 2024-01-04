package ra.academy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.academy.exception.UsernameAndPasswordIncorrectException;
import ra.academy.model.dto.request.auth.SignInRequest;
import ra.academy.model.dto.request.auth.SignUpRequest;
import ra.academy.model.dto.response.*;
import ra.academy.model.entity.Category;
import ra.academy.service.ICategoryService;
import ra.academy.service.IProductService;
import ra.academy.service.IUserService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@EnableSpringDataWebSupport
@RequestMapping("/api.myservice.com/v1")
public class PermitAllController {
    private final IUserService userService;
    private final ICategoryService categoryService;
    private final IProductService productService;

//    signIn
    @PostMapping("/auth/sign-in")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) throws UsernameAndPasswordIncorrectException {
        return new ResponseEntity<>(userService.singIn(signInRequest), HttpStatus.CREATED);
    }

//    signUp
    @PostMapping("/auth/sign-up")
    public ResponseEntity<MesResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        userService.signUp(signUpRequest);
        return new ResponseEntity<>(new MesResponse("register successful !"),HttpStatus.OK);
    }

//    category findAll
    @GetMapping("/categories")
    public ResponseEntity<DataResponse<CategoryResponse>> catFindAll(){
        return ResponseEntity.ok(new DataResponse<>(categoryService.findAllCategory(true)));
    }

//    find product by name or description
    @GetMapping("/products/search")
    public ResponseEntity<DataResponse<ProductResponse>> productFindBySearch(@RequestParam("searchName") String search){
        return ResponseEntity.ok(new DataResponse<>(productService.findAllBySearch(search)));
    }
//    find all product have pageable
    @GetMapping("/products")
    public ResponseEntity<PageDataResponse<ProductResponse>> productFindAll(Pageable pageable){
        return ResponseEntity.ok(productService.findAll(pageable));
    }
    // find product by id
    @GetMapping("/products/{productId}")
    public ResponseEntity<DataResponse<ProductResponse>> productFindById(@PathVariable Long productId){
        DataResponse<ProductResponse> p = new DataResponse<>(new ArrayList<>());
        p.getData().add(productService.findProById(productId));
        return ResponseEntity.ok(p);
    }
    // find product by category
    @GetMapping("/products/categories/{categoryId}")
    public ResponseEntity<DataResponse<ProductResponse>> productFindByCategory(@PathVariable(name = "categoryId") Category category){
        return ResponseEntity.ok(new DataResponse<>(productService.findByCategory(category)));
    }
    // danh sach san pham moi
    @GetMapping("/products/new-products")
    public ResponseEntity<DataResponse<ProductResponse>> getpAllProByCreateAt(){
        return ResponseEntity.ok(new DataResponse<>(productService.getByCreateAt()));
    }
    // danh sach san pham noi bat
    @GetMapping("/products/featured-products")
    ResponseEntity<DataResponse<ProductResponse>> getFeaturedProducts(){
        return ResponseEntity.ok(new DataResponse<>(productService.getFeaturedProducts()));
    }
}
