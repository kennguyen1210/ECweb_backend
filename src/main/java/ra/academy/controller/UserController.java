package ra.academy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.academy.model.dto.request.address.AddressRequest;
import ra.academy.model.dto.request.shoppingCart.ShoppingCartRequest;
import ra.academy.model.dto.request.user.AccountRequest;
import ra.academy.model.dto.request.user.PasswordChangeRequest;
import ra.academy.model.dto.response.*;
import ra.academy.model.entity.Address;
import ra.academy.model.entity.Product;
import ra.academy.model.entity.User;
import ra.academy.model.entity.WishList;
import ra.academy.security.principle.CustomUserDetail;
import ra.academy.service.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@EnableSpringDataWebSupport
@RequestMapping("/api.myservice.com/v1/user")
public class UserController {
    private final IUserService userService;
    private final IWishListService wishListService;
    private final IShoppingCartService shoppingCartService;
    private final IAddressService addressService;
    private final IOrderService orderService;

    //======= wish list
    // them moi 1 wishlist
    @PostMapping("/wish-list")
    public ResponseEntity<MesResponse> addWishList(@RequestBody Product product){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetail.getUserId());
        wishListService.save(new WishList(null, User.builder().userId(userDetail.getUserId()).build(),product));
        return ResponseEntity.ok(new MesResponse("Add success!"));
    }
    // lay ve danh sach san pham yeu thich
    @GetMapping("/wish-list")
    public ResponseEntity<DataResponse<WishListResponse>> getWishList(){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new DataResponse<>(wishListService.getAllWishList(userDetail.getUserId())));
    }
    // xoa san pham ra khoi danh sach yeu thich
    @DeleteMapping("/wish-list/{wishListId}")
    public ResponseEntity<MesResponse> deleteWishList(@PathVariable Long wishListId){
        wishListService.detele(wishListId);
        return ResponseEntity.ok(new MesResponse("Remove item success!"));
    }
    //===============================

    //========= shopping cart
    // thêm san pham vao gio hang
    @PostMapping("/shopping-cart")
    public ResponseEntity<DataResponse<ShoppingCartResponse>> addShoppingCart(@RequestBody ShoppingCartRequest shoppingCartRequest){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DataResponse<ShoppingCartResponse> d = new DataResponse<>(new ArrayList<>());
        d.getData().add(shoppingCartService.saveShoppingCart(userDetail.getUserId(),shoppingCartRequest.getProductId(),shoppingCartRequest.getQuantity()));
        return ResponseEntity.ok(d);
    }
    // danh sach san pham trong gio hang
    @GetMapping("/shopping-cart")
    public ResponseEntity<DataResponse<ShoppingCartResponse>> getShoppingCart(){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new DataResponse<>(shoppingCartService.getAllByUser(userDetail.getUserId())));
    }
    // thoi doi so luong san pham trong gio hang
    @PutMapping("/shopping-cart/{cartItemId}")
    public ResponseEntity<DataResponse<ShoppingCartResponse>> changeQuantity(@PathVariable Integer cartItemId,@RequestBody ShoppingCartRequest shoppingCartRequest){
        DataResponse<ShoppingCartResponse> d = new DataResponse<>(new ArrayList<>());
        d.getData().add(shoppingCartService.changeQuantity(cartItemId,shoppingCartRequest.getQuantity()));
        return ResponseEntity.ok(d);
    }
    // xoa 1 san pham trong 1 gio hang
    @DeleteMapping("/shopping-cart/{cartItemId}")
    public ResponseEntity<MesResponse> deleteCartItem(@PathVariable Integer cartItemId){
        shoppingCartService.deleteCartItem(cartItemId);
        return new ResponseEntity<>(new MesResponse("Remove item success!"), HttpStatus.OK);
    }
    // xoa toan bo san pham trong gio hang
    @DeleteMapping("/shopping-cart")
    public ResponseEntity<MesResponse> deleteAllCartItem(){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        shoppingCartService.deleteAllCartItem(userDetail.getUserId());
        return new ResponseEntity<>(new MesResponse("Remove all item success!"),HttpStatus.OK);
    }
    // dat hang (chua xong!!!)
    @PostMapping("/shopping-cart/checkout")
    public ResponseEntity<DataResponse<OrderResponse>> order(@RequestBody Address address){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DataResponse<OrderResponse> d = new DataResponse<>(new ArrayList<>());
        d.getData().add(orderService.checkOut(userDetail.getUserId(), address));
        return  ResponseEntity.ok(d);
    }
    //=============================

    //=========== account
    // lay ve thong tin account
    @GetMapping("/account")
    public ResponseEntity<DataResponse<AccountResponse>> getAccunt(){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DataResponse<AccountResponse> d = new DataResponse<>(new ArrayList<>());
        d.getData().add(userService.getAccount(userDetail.getUserId()));
        return ResponseEntity.ok(d);
    }
    // thay doi thong tin account
    @PutMapping("/account")
    public ResponseEntity<DataResponse<AccountResponse>> changeInfo(@Valid @ModelAttribute AccountRequest acc){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DataResponse<AccountResponse> d = new DataResponse<>(new ArrayList<>());
        d.getData().add(userService.saveChangeInfo(acc,userDetail.getUserId()));
        return ResponseEntity.ok(d);
    }
    // thay doi mat khau
    @PutMapping("/account/change-password")
    public ResponseEntity<MesResponse> changePass(@Valid @RequestBody PasswordChangeRequest p){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changePass(p,userDetail.getUserId());
        return ResponseEntity.ok(new MesResponse("Change Password success!"));
    }
    // lay ve danh sach address
    @GetMapping("/account/address")
    public ResponseEntity<DataResponse<AddressResponse>> getAllAddress(){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new DataResponse<>(addressService.findAllByUserId(userDetail.getUserId())));
    }
    // them moi 1 address cho account
    @PostMapping("/account/address")
    public ResponseEntity<DataResponse<AddressResponse>> addNewAddress(@Valid @RequestBody AddressRequest addressRequest){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        addressRequest.setUserId(userDetail.getUserId());
        DataResponse<AddressResponse> d = new DataResponse<>(new ArrayList<>());
        d.getData().add(addressService.allNewAddress(addressRequest));
        return ResponseEntity.ok(d);
    }
    // xoa 1 address theo ma address
    @DeleteMapping("/account/address/{addressId}")
    public ResponseEntity<MesResponse> deleteAddress(@PathVariable Long addressId){
        addressService.detele(addressId);
        return ResponseEntity.ok(new MesResponse("Delete successfully"));
    }
    // lay ra address theo ma address
    @GetMapping("/account/address/{addressId}")
    public ResponseEntity<DataResponse<AddressResponse>> getAddressbyId(@PathVariable Long addressId){
        DataResponse<AddressResponse> d = new DataResponse<>(new ArrayList<>());
        d.getData().add(addressService.findByAddressId(addressId));
        return ResponseEntity.ok(d);
    }
    //====================

    //=========== history
    // danh sach lich su mua hang
    @GetMapping("/history")
    public ResponseEntity<DataResponse<HistoryResponse>> getAllHistory(){
        return ResponseEntity.ok(new DataResponse<>(orderService.getAllHistory()));
    }
    // chi tiet don hang
    @GetMapping("/history/serialNumber")
    public ResponseEntity<DataResponse<HistoryDetailResponse>> getHistoryDetail(@RequestParam String serialNumber){
        DataResponse<HistoryDetailResponse> d = new DataResponse<>(new ArrayList<>());
        d.getData().add(orderService.getHistoryDetail(serialNumber));
        return ResponseEntity.ok(d);
    }
    // danh sach lich su mua hang theo trang thai
    @GetMapping("/history/{orderStatus}")
    public ResponseEntity<DataResponse<HistoryResponse>> getHistoryByStatus(@PathVariable String orderStatus){
        return ResponseEntity.ok(new DataResponse<>(orderService.getAllHistoryByStatus(orderStatus)));
    }
    // huy don hang trong trang thai cho xac nhan
    @PutMapping("/history/{serialNumber}/cancel")
    public ResponseEntity<MesResponse> cancelOrder( @PathVariable String serialNumber){
        // logic
        orderService.cancelOrder(serialNumber);
        return ResponseEntity.ok(new MesResponse("Cancel success!"));
    }
    //======================
}
