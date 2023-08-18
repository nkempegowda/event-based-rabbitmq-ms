package com.event.stockservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.event.stockservice.dto.OrderEvent;

@Service
public class OrderConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);


  @RabbitListener(queues = "${rabbitmq.order.queue.name}")
  public void consume(OrderEvent orderEvent)
  {
    LOGGER.info(String.format("Order event received. %s",orderEvent));


  }
}
