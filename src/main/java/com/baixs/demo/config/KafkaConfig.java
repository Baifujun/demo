package com.baixs.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("testtopic", 3, (short) 2);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("topic1", 3, (short) 2);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("topic2", 3, (short) 2);
    }

    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            System.out.println("消费异常："+message.getPayload());
            return null;
        };
    }
}
