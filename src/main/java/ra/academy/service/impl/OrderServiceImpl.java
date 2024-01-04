package ra.academy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.academy.model.dto.response.*;
import ra.academy.model.entity.*;
import ra.academy.repository.*;
import ra.academy.service.IOrderService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final IAddressRepository addressRepository;
    private final IShoppingCartRepository shoppingCartRepository;
    private final IOrderDetailRepository orderDetailRepository;
    @Value("${order.receive-at}")
    private long receiveAt;
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Order not found!"));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void detele(Long id) {
        orderRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Order not found!"));
        orderRepository.deleteById(id);
    }

    @Override
    public PageDataResponse<Order> findAllOrder(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return PageDataResponse.<Order>builder()
                .content(orders.getContent())
                .number(orders.getNumber())
                .size(orders.getSize())
                .sort(orders.getSort())
                .totalPages(orders.getTotalPages())
                .build();
    }

    @Override
    public PageDataResponse<Order> findAllByStatus(OrderStatus status, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByStatus(status,pageable);
        return PageDataResponse.<Order>builder()
                .content(orders.getContent())
                .number(orders.getNumber())
                .size(orders.getSize())
                .sort(orders.getSort())
                .totalPages(orders.getTotalPages())
                .build();
    }

    @Override
    public OrderResponse checkOut(String userId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found!"));
        Address add = addressRepository.findById(address.getAddressId()).orElseThrow(()-> new NoSuchElementException("not authorize"));
        try {
            List<ShoppingCart> list = shoppingCartRepository.findAllByUserUserId(userId);
            Long total = list.stream().map(e->e.getOrderQuantity()*e.getProduct().getUnitPrice().longValue()).reduce(0L, Long::sum);
            Order order = orderRepository.save(Order.builder()
                    .serialNumber(UUID.randomUUID().toString())
                    .createAt(new Date())
                    .receivedAt(new Date(System.currentTimeMillis()+receiveAt))
                    .userId(user.getUserId())
                    .note("")
                    .receiveName(add.getReceiveName())
                    .receiveAddress(add.getFullAddress())
                    .receivePhone(add.getPhone())
                    .status(OrderStatus.WAITING)
                    .totalPrice(BigDecimal.valueOf(total))
                    .build());
            List<OrderDetail> orderDetails = orderDetailRepository.saveAll(list.stream()
                    .map(e-> OrderDetail.builder()
                            .orderDetailId(new OrderDetailId(order.getOrderId(),e.getProduct().getProductId()))
                            .order(order)
                            .product(e.getProduct())
                            .orderQuantity(e.getOrderQuantity())
                            .name(e.getProduct().getProductName())
                            .unitPrice(e.getProduct().getUnitPrice())
                            .build())
                    .toList());
            shoppingCartRepository.deleteAllByUserUserId(user.getUserId());
            return OrderResponse.builder()
                    .serialNumber(order.getSerialNumber())
                    .createAt(new SimpleDateFormat("yyyy-MM-dd").format(order.getCreateAt()))
                    .receivedAt(new SimpleDateFormat("yyyy-MM-dd").format(order.getReceivedAt()))
                    .note(order.getNote())
                    .status(order.getStatus().name())
                    .receiveName(order.getReceiveName())
                    .receiveAddress(order.getReceiveAddress())
                    .receivePhone(order.getReceivePhone())
                    .totalPrice(order.getTotalPrice())
                    .build();
        } catch (Exception e){
            throw new RuntimeException("not authorize");
        }

    }

    @Override
    public List<HistoryResponse> getAllHistory() {
        try {
            List<Order> list = orderRepository.findAll();
            return list.stream().map(e->HistoryResponse.builder()
                            .serialNumber(e.getSerialNumber())
                            .totalPrice(e.getTotalPrice())
                            .status(e.getStatus().name())
                            .createAt(new SimpleDateFormat("yyyy-MM-dd").format(e.getCreateAt()))
                            .note(e.getNote())
                            .build())
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("not authorize");
        }
    }

    @Override
    public List<HistoryResponse> getAllHistoryByStatus(String orderStatus) {
        OrderStatus status = null;
        switch (orderStatus.toUpperCase()){
            case "WAITING":
                status = OrderStatus.WAITING;
                break;
            case "CONFIRM":
                status = OrderStatus.CONFIRM;
                break;
            case "DELIVERY":
                status = OrderStatus.DELIVERY;
                break;
            case "SUCCESS":
                status = OrderStatus.SUCCESS;
                break;
            case "CANCEL":
                status = OrderStatus.CANCEL;
                break;
            case "DENIED":
                status = OrderStatus.DENIED;
                break;
        }
        try {
            List<Order> list = orderRepository.findAllByStatus(status);
            return list.stream().map(e->HistoryResponse.builder()
                            .serialNumber(e.getSerialNumber())
                            .totalPrice(e.getTotalPrice())
                            .status(e.getStatus().name())
                            .createAt(new SimpleDateFormat("yyyy-MM-dd").format(e.getCreateAt()))
                            .note(e.getNote())
                            .build())
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("not authorize");
        }
    }

    @Override
    public HistoryDetailResponse getHistoryDetail(String serialNumber) {
        Order order = orderRepository.findBySerialNumber(serialNumber).orElseThrow(()-> new NoSuchElementException("Not authorize!"));
        List<Item> list = orderDetailRepository.getAllItem();
        return HistoryDetailResponse.builder()
                .serialNumber(order.getSerialNumber())
                .status(order.getStatus().name())
                .createAt(new SimpleDateFormat("yyyy-MM-dd").format(order.getCreateAt()))
                .receivedAt(new SimpleDateFormat("yyyy-MM-dd").format(order.getReceivedAt()))
                .listItem(list)
                .totalPrice(order.getTotalPrice())
                .note(order.getNote())
                .receiveAddress(order.getReceiveAddress())
                .receiveName(order.getReceiveName())
                .receivePhone(order.getReceivePhone())
                .build();
    }


    @Override
    public void cancelOrder(String number) {
        Order order = orderRepository.findBySerialNumber(number).orElseThrow(()-> new NoSuchElementException("Order not found!"));
        if(order.getStatus().name().equals("WAITING")){
            order.setStatus(OrderStatus.CANCEL);
        } else {
            throw new RuntimeException("not authorize");
        }
    }
}
