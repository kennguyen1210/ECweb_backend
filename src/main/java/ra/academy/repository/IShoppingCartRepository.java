package ra.academy.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ra.academy.model.dto.response.OrderTotalPrice;
import ra.academy.model.dto.response.ShoppingCartResponse;
import ra.academy.model.entity.ShoppingCart;
import ra.academy.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@EnableTransactionManagement
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
    @Query("select new ra.academy.model.dto.response.ShoppingCartResponse(s.shoppingCartId,p.productName,p.unitPrice,s.orderQuantity)" +
            " from ShoppingCart s join Product p on s.product.productId = p.productId where s.user.userId = :userId")
    List<ShoppingCartResponse> findAllByUser(String userId);
//    @Modifying
//    @Transactional
//    @Query("delete from ShoppingCart s where s.user.userId = :userId")
    @Transactional
    void deleteAllByUserUserId(String userId);
//    @Query("select new ra.academy.model.dto.response.OrderTotalPrice(sum(s.orderQuantity*s.product.unitPrice)) from ShoppingCart s where s.user.userId = :userId group by s.user.userId")
//    OrderTotalPrice getTotalPriceByUserId(String userId);

    List<ShoppingCart> findAllByUserUserId(String userId);
}
