package com.callmextrm.notification_microservice;

import org.callmextrm.events.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationConsumer {
    @KafkaListener(topics = "order_created", groupId = "notification_service")
    public void onOrderCreated(OrderCreatedEvent event){
        System.out.println("ADMIN NOTIFICATION: New Order"
                + event.orderId()+"created by: "
                + event.username() + "roles = " + event.roles());

    }



}
