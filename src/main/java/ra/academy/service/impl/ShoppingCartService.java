package ra.academy.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.academy.model.dto.response.ShoppingCartResponse;
import ra.academy.model.entity.Product;
import ra.academy.model.entity.ShoppingCart;
import ra.academy.model.entity.User;
import ra.academy.repository.IProductRepository;
import ra.academy.repository.IShoppingCartRepository;
import ra.academy.repository.IUserRepository;
import ra.academy.service.IProductService;
import ra.academy.service.IShoppingCartService;
import ra.academy.service.IUserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements IShoppingCartService {
    private final IShoppingCartRepository shoppingCartRepository;
    private final IUserService userService;
    private final IProductService productService;
    @Override
    public List<ShoppingCart> findAll() {
        return null;
    }

    @Override
    public ShoppingCart findById(Integer id) {
        return null;
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public void detele(Integer id) {

    }

    @Override
    public ShoppingCartResponse saveShoppingCart(String userId, Long productId, Integer quantity) {
        User user = userService.findById(userId);
        Product product = productService.findById(productId);
        ShoppingCart s;
        try {
            s = shoppingCartRepository.save(new ShoppingCart(null,product,user,quantity));
        } catch (Exception e){
            throw new RuntimeException("not authorize!");
        }
        return ShoppingCartResponse.builder()
                .id(s.getShoppingCartId())
                .productName(product.getProductName())
                .unitPrice(product.getUnitPrice())
                .quantity(s.getOrderQuantity())
                .build();
    }

    @Override
    public List<ShoppingCartResponse> getAllByUser(String userId) {
        return shoppingCartRepository.findAllByUser(userId);
    }

    @Override
    public ShoppingCartResponse changeQuantity(Integer cartItemId, Integer quantity) {
        ShoppingCart s = shoppingCartRepository.findById(cartItemId).orElseThrow(()-> new RuntimeException("Not Authenticated !"));
        s.setOrderQuantity(quantity);
        ShoppingCart cart = shoppingCartRepository.save(s);
        return ShoppingCartResponse.builder()
                .id(cart.getShoppingCartId())
                .productName(cart.getProduct().getProductName())
                .unitPrice(cart.getProduct().getUnitPrice())
                .quantity(cart.getOrderQuantity())
                .build();
    }

    @Override
    public void deleteCartItem(Integer cartItemId) {
        shoppingCartRepository.findById(cartItemId).orElseThrow(()-> new RuntimeException("Not Authenticated !"));
        shoppingCartRepository.deleteById(cartItemId);
    }

    @Override
    public void deleteAllCartItem(String userId) {
        shoppingCartRepository.deleteAllByUserUserId(userId);
    }
}
