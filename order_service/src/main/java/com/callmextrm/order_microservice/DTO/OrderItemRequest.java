package com.callmextrm.order_microservice.DTO;

public record OrderItemRequest(
        Long productId,
        String productName,
        Integer quantity
) {
}
