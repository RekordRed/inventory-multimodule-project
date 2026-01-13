package com.callmextrm.product_service.kafka;

import org.callmextrm.events.ProductCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductEventProducer {
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductEventProducer(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishProductCreated(ProductCreatedEvent event) {
        kafkaTemplate.send("product_created", event.productId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.err.println("Kafka publish failed" + ex.getMessage());
                    } else {
                        System.out.println("Published product_created for product_id = " + event.productId());
                    }


                });
    }


}
