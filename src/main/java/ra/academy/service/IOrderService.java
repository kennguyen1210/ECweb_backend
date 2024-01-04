package ra.academy.service;

import org.springframework.data.domain.Pageable;
import ra.academy.model.dto.response.HistoryDetailResponse;
import ra.academy.model.dto.response.HistoryResponse;
import ra.academy.model.dto.response.OrderResponse;
import ra.academy.model.dto.response.PageDataResponse;
import ra.academy.model.entity.Address;
import ra.academy.model.entity.Order;
import ra.academy.model.entity.OrderStatus;

import java.util.List;

public interface IOrderService extends IGenericService<Order,Long>{
    PageDataResponse<Order> findAllOrder(Pageable pageable);
    PageDataResponse<Order> findAllByStatus(OrderStatus status, Pageable pageable);

    OrderResponse checkOut(String userId, Address address);

    List<HistoryResponse> getAllHistory();

    List<HistoryResponse> getAllHistoryByStatus(String orderStatus);

    HistoryDetailResponse getHistoryDetail(String serialNumber);
    void cancelOrder(String serialNumber);
}
