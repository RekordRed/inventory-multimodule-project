package com.callmextrm.order_microservice.repository;

import com.callmextrm.order_microservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
