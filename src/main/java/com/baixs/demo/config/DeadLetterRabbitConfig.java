package com.baixs.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DeadLetterRabbitConfig {
    @Bean
    public Queue bizQueue() {
        // 业务队列绑定死信交换机
        Map<String, Object> properties = new HashMap<>();
        properties.put("x-dead-letter-exchange", "deadDirectExchange");
        properties.put("x-dead-letter-routing-key", "dead");
        return new Queue("bizQueue", true, false, false, properties);
    }

    @Bean
    public Queue deadQueue() {
        return new Queue("deadQueue");
    }

    @Bean
    public DirectExchange bizDirectExchange() {
        return new DirectExchange("bizDirectExchange");
    }

    @Bean
    public DirectExchange deadDirectExchange() {
        return new DirectExchange("deadDirectExchange");
    }

    @Bean
    public Binding bizBinding() {
        return BindingBuilder.bind(bizQueue()).to(bizDirectExchange()).with("biz");
    }

    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadDirectExchange()).with("dead");
    }

}
