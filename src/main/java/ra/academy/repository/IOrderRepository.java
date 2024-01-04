package ra.academy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ra.academy.model.entity.Order;
import ra.academy.model.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findAll(Pageable pageable);
    Page<Order> findAllByStatus(OrderStatus status, Pageable pageable);
    List<Order> findAllByStatus(OrderStatus status);
    Optional<Order> findBySerialNumber(String number);

}
