package com.baixs.demo.controller;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class RabbitController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "direct message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("directExchange", "directRouting", map, new CorrelationData(String.valueOf(System.currentTimeMillis())));
        // 不管消息是否到达交换机都会调用confirmCallback，达到时ack为true，未到达时ack未false
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> System.out.println("confirmCallback..." + ack + cause)));
        // 消息到达队列时不会调用returnCallBack，未到达时队列时会调用returnCallBack
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> System.out.println("returnCallback..." + message + replyCode + replyText + exchange + routingKey));
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> System.out.println("confirmCallback..." + ack + cause)));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> System.out.println("returnCallback..." + message + replyCode + replyText + exchange + routingKey));
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> System.out.println("confirmCallback..." + ack + cause)));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> System.out.println("returnCallback..." + message + replyCode + replyText + exchange + routingKey));
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> System.out.println("confirmCallback..." + ack + cause)));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> System.out.println("returnCallback..." + message + replyCode + replyText + exchange + routingKey));
        return "ok";
    }

    @GetMapping("/sendDeadMessage")
    public String sendDeadMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "dead message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("bizDirectExchange", "biz", map, new CorrelationData(String.valueOf(System.currentTimeMillis())));
        // 不管消息是否到达交换机都会调用confirmCallback，达到时ack为true，未到达时ack未false
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> System.out.println("confirmCallback..." + ack + cause)));
        // 消息到达队列时不会调用returnCallBack，未到达时队列时会调用returnCallBack
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> System.out.println("returnCallback..." + message + replyCode + replyText + exchange + routingKey));
        return "ok";
    }
}
