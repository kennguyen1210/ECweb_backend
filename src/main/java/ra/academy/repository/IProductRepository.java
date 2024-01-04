package ra.academy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ra.academy.model.entity.Category;
import ra.academy.model.entity.Product;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String productName, String description);
    Page<Product> findAll(Pageable pageable);
    List<Product> findAllByCategory(Category category);
    boolean existsByProductName(String productName);

    @Query("select p from Product p order by p.create_at desc limit 10")
    List<Product> findAllByCreate_at();

//    @Query(nativeQuery = true,value = "select p.* from products p" +
//            " join order_detail od on p.product_id = od.product_id" +
//            " join orders o on od.order_id = o.order_id" +
//            " where month(o.create_at) = month(curdate()) and year(o.create_at) = year(curdate())" +
//            " group by p.product_id" +
//            " order by sum(od.order_quantity) desc" +
//            "  limit 5")
    @Query("select p from Product p join OrderDetail od on p.productId = od.product.productId join Order o on o.orderId = od.order.orderId where month(o.createAt) = month(current date) and year(o.createAt) = year(current date ) group by p.productId order by sum(od.orderQuantity) limit 5")
    List<Product> findAllBestSellerByMonth();

}
