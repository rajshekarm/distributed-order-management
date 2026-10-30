package com.example.ordermanagement.config;

import com.example.ordermanagement.order.OrderService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    NewTopic orderEventsTopic() {
        return TopicBuilder.name(OrderService.ORDER_EVENTS_TOPIC)
                .partitions(6)
                .replicas(1)
                .build();
    }
}
