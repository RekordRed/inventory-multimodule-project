package org.callmextrm.events;

public record ProductCreatedEvent(Long productId, Integer initialQuantity) {
}
