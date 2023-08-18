package com.event.orderservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.orderservice.dto.Order;
import com.event.orderservice.dto.OrderEvent;
import com.event.orderservice.publisher.OrderProducer;

@RestController
@RequestMapping("api/v1/")
public class OrderController {

  @Autowired
  private OrderProducer orderProducer;

  @PostMapping("publish")
  public ResponseEntity<String> placeOrder(@RequestBody Order order)
  {
    order.setOrderId(UUID.randomUUID().toString());
    OrderEvent orderEvent = new OrderEvent();
    orderEvent.setOrder(order);
    orderEvent.setStatus("PENDING");
    orderEvent.setMessage("Order is in pending status");

    orderProducer.sendMessage(orderEvent);
    return ResponseEntity.ok("Order sent to RabbitMQ");
  }
}
