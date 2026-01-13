package com.callmextrm.order_microservice;

import com.callmextrm.order_microservice.DTO.CreateOrderRequest;
import com.callmextrm.order_microservice.DTO.OrderItemRequest;
import com.callmextrm.order_microservice.entity.Order;
import com.callmextrm.order_microservice.entity.OrderItem;
import com.callmextrm.order_microservice.entity.Status;
import com.callmextrm.order_microservice.kafka.OrderEventProducer;
import com.callmextrm.order_microservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.callmextrm.events.OrderCreatedEvent;
import org.callmextrm.events.OrderLines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderDao;
    private final OrderEventProducer producer;


    public Order createOrder(CreateOrderRequest request) {
        // LATER: replace with real values from JWT / SecurityContext

        Long userId = 1L;
        String username = "ahmed";
        String roles = "ADMIN";
        log.info("Creating order for user={}, with roles={}", username, roles);


        // 1) Create order
        Order order = Order.builder()
                .userId(userId)
                .username(username)
                .roles(roles)
                .status(Status.CREATED).build();


        // 2) Add items (using helper method so FK is set)

        for (OrderItemRequest itemRequest : request.items()) {
            OrderItem item = OrderItem.builder()
                    .productId(itemRequest.productId())
                    .productName(itemRequest.productName())
                    .quantity(itemRequest.quantity()).build();
            order.addItem(item);
        }
        // 3) Save once (cascade saves items)

        Order saved = orderDao.save(order);
        log.info("Order saved with id={}", saved.getId());

        // 4) Publish event to Kafka
        List<OrderLines> lines = saved.getItems().stream()
                .map(i -> new OrderLines(i.getProductId(), i.getProductName(), i.getQuantity()))
                .toList();

        producer.publishOrderCreated(new OrderCreatedEvent(saved.getId(), saved.getUserId(), saved.getUsername(), saved.getRoles(), lines));
        log.info("OrderCreatedEvent sent to Kafka for orderId={}", saved.getId());
        return saved;
    }

}
