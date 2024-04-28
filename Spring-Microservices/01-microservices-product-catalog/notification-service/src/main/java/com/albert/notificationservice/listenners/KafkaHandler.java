package com.albert.notificationservice.listenners;

import com.albert.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaHandler
{
    @KafkaListener(topics = {"notificationTopic"})
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        log.info("Order Number: {}", orderPlacedEvent);
    }
}
