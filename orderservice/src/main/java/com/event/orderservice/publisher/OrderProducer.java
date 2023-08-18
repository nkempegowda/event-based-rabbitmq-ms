package com.event.orderservice.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.event.orderservice.dto.OrderEvent;

@Service
public class OrderProducer {
    private Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${rabbitmq.order.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.order.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.email.routing.key}")
    private String emailRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(OrderEvent orderEvent)
    {
        LOGGER.info(String.format("Order event sent to rabbit mq %s", orderEvent));
        rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
        rabbitTemplate.convertAndSend(exchange, emailRoutingKey, orderEvent);
    }
}
