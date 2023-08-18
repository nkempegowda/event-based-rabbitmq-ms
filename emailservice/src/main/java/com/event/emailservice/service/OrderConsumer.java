package com.event.emailservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.event.emailservice.dto.OrderEvent;

@Service
public class OrderConsumer {

  private Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

  @RabbitListener(queues = "${rabbitmq.email.queue.name}")
  public void consume(OrderEvent orderEvent)
  {
    LOGGER.info(String.format("Received email event %s",orderEvent));
  }
}
