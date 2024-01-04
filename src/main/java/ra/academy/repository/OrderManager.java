package ra.academy.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ra.academy.model.entity.Product;

import java.util.List;
import java.util.Queue;

@Repository
public class OrderManager {
    @PersistenceContext
    private EntityManager entityManager;
    public List<Product> getProductBestSellerInThisMonth(){
        Query query = entityManager.createNativeQuery("select p.* from products p" +
                " join order_detail od on p.product_id = od.product_id" +
                " join orders o on od.order_id = o.order_id" +
                " where month(o.create_at) = month(curdate()) and year(o.create_at) = year(curdate())" +
                " group by p.product_id" +
                " order by sum(od.order_quantity) desc" +
                " limit 5",Product.class);

        return query.getResultList();
    }
}
