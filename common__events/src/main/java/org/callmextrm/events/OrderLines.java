package org.callmextrm.events;

public record OrderLines(
        Long productId,
        String productName,
        Integer quantity
) {
}
