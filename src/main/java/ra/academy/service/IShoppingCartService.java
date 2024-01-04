package ra.academy.service;

import ra.academy.model.dto.response.ShoppingCartResponse;
import ra.academy.model.entity.ShoppingCart;

import java.util.List;

public interface IShoppingCartService extends IGenericService<ShoppingCart,Integer>{
    ShoppingCartResponse saveShoppingCart(String userId,Long productId,Integer quantity);
    List<ShoppingCartResponse> getAllByUser(String userId);
    ShoppingCartResponse changeQuantity(Integer cartItemId,Integer quantity);

    void deleteCartItem(Integer cartItemId);
    void deleteAllCartItem(String userId);
}
