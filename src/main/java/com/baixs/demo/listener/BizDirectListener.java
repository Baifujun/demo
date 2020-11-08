package com.baixs.demo.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RabbitListener(queues = {"bizQueue"})
public class BizDirectListener {

    @RabbitHandler
    public void onMessage(Map map, Message message, Channel channel) throws IOException {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("BizDirectExchange消息：" + map);
            int a = 1 / 0;
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.out.println("消费消息出现了异常！！");
            channel.basicReject(tag, false);
        }
    }
}
