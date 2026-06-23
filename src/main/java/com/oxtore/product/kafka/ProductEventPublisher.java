package com.oxtore.product.kafka;

import com.oxtore.product.DTOs.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static com.oxtore.product.config.KafkaProducerConfig.PRODUCT_CREATED_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventPublisher {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public void publishProductCreated(ProductCreatedEvent event) {
        // using productId as the message key so all events for the same product
        // land on the same partition (preserves ordering per product)
        String key = "1";

        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
                kafkaTemplate.send(PRODUCT_CREATED_TOPIC, key, event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Published ProductCreatedEvent [key={}, partition={}, offset={}]",
                        key,
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Failed to publish ProductCreatedEvent [key={}]", key, ex);
            }
        });
    }
}