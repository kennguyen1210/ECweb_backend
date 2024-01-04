package ra.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ra.academy.model.dto.response.Item;
import ra.academy.model.entity.OrderDetail;
import ra.academy.model.entity.OrderDetailId;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    @Override
    <S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

    @Query("select new ra.academy.model.dto.response.Item(p.productName,p.image,p.unitPrice,od.orderQuantity) from OrderDetail od join Product p on od.product.productId = p.productId")
    List<Item> getAllItem();

}
