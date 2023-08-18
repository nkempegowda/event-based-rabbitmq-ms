package com.event.orderservice.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.order.queue.name}")
  private String orderQueueName;

  @Value("${rabbitmq.order.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.order.routing.key}")
  private String routingKey;

  @Value("${rabbitmq.email.queue.name}")
  private String emailQueue;

  @Value("${rabbitmq.email.routing.key}")
  private String emailRoutingKey;



  @Bean
  public Queue orderQueue()
  {
    return new Queue(orderQueueName);
  }

  @Bean
  public Queue emailQueue()
  {
    return new Queue(emailQueue);
  }

  @Bean
  public TopicExchange exhange()
  {
    return new TopicExchange(exchange);
  }

  @Bean
  public Binding binding()
  {
   return BindingBuilder.bind(orderQueue()).to(exhange()).with(routingKey);
  }

  @Bean
  public Binding emailBinding()
  {

    return BindingBuilder.bind(emailQueue()).to(exhange()).with(emailRoutingKey);
  }

  @Bean
  public MessageConverter converter()
  {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
  {

    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return  rabbitTemplate;
  }
}
