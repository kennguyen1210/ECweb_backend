package ra.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.academy.model.dto.response.WishListResponse;
import ra.academy.model.entity.Product;
import ra.academy.model.entity.WishList;

import java.util.List;

@Repository
public interface IWishListRepository extends JpaRepository<WishList,Long> {
    @Query("select new ra.academy.model.dto.response.WishListResponse(w.wishListId,p.productId,p.productName,p.image)" +
            " from WishList w join Product p on w.product.productId = p.productId where w.user.userId = :userId")
    List<WishListResponse> findAllByUser(String userId);
}
