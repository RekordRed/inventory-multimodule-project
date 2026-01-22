package com.callmextrm.order_microservice;

import com.callmextrm.order_microservice.DTO.CreateOrderRequest;
import com.callmextrm.order_microservice.DTO.OrderItemRequest;
import com.callmextrm.order_microservice.entity.Order;
import com.callmextrm.order_microservice.entity.OrderItem;
import com.callmextrm.order_microservice.entity.Status;
import com.callmextrm.order_microservice.kafka.OrderEventProducer;
import com.callmextrm.order_microservice.productClient.ProductClient;
import com.callmextrm.order_microservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.callmextrm.events.OrderCreatedEvent;
import org.callmextrm.events.OrderLines;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderDao;
    private final OrderEventProducer producer;
    private final ProductClient productClient;

    private Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Order createOrder(CreateOrderRequest request) {


        Authentication auth = getAuth();
        Jwt jwt = (Jwt) auth.getPrincipal();


        String username =jwt.getClaimAsString("preferred_username");
        String roles = auth.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).filter(a -> !a.startsWith("SCOPE_")).collect(Collectors.joining(","));
        
        log.info("Creating order for user={}, with roles={}", username, roles);


        Order order = Order.builder()
                .username(username)
                .roles(roles)
                .createdAt(Instant.now())
                .status(Status.CREATED).build();


        for (OrderItemRequest itemRequest : request.items()) {
            productClient.assertProductExists(itemRequest.productId());
            OrderItem item = OrderItem.builder()
                    .productId(itemRequest.productId())
                    .quantity(itemRequest.quantity())
                    .build();
            order.addItem(item);


        }




        Order saved = orderDao.save(order);
        log.info("Order saved with id={}", saved.getId());

        List<OrderLines> lines = saved.getItems().stream()
                .map(i -> new OrderLines(i.getProductId(), i.getProductName(), i.getQuantity()))
                .toList();

        producer.publishOrderCreated(new OrderCreatedEvent(saved.getId(), saved.getUsername(), saved.getRoles(), lines));
        log.info("OrderCreatedEvent sent to Kafka for orderId={}", saved.getId());
        return saved;
    }

}