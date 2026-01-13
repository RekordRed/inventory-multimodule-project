package com.callmextrm.order_microservice.DTO;

import java.util.List;

public record CreateOrderRequest(
        List<OrderItemRequest> items) {
}
