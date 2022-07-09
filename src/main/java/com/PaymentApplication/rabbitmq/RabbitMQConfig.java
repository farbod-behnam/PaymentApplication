package com.PaymentApplication.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig
{

    @Value("${onlineshop.app.rabbitmq.exchange}")
    private String ONLINE_SHOP_EXCHANGE;

    @Value("${onlineshop.app.rabbitmq.queue.order}")
    private String ONLINE_SHOP_ORDER_QUEUE;

    @Value("${onlineshop.app.rabbitmq.routingKey.order}")
    private String ORDER_ROUTING_KEY;

    @Value("${onlineshop.app.rabbitmq.queue.user}")
    private String ONLINE_SHOP_USER_QUEUE;

    @Value("${onlineshop.app.rabbitmq.routingKey.user}")
    private String USER_ROUTING_KEY;

    @Bean
    public TopicExchange onlineShopExchange()
    {
        return new TopicExchange(ONLINE_SHOP_EXCHANGE);
    }

    @Bean
    public Queue onlineShopOrderQueue()
    {
        return new Queue(ONLINE_SHOP_ORDER_QUEUE);
    }

    @Bean
    public Queue onlineShopUserQueue()
    {
        return new Queue(ONLINE_SHOP_USER_QUEUE);
    }

    @Bean
    public Binding onlineShopOrderBinding(Queue onlineShopOrderQueue, TopicExchange onlineShopExchange)
    {
        return BindingBuilder.bind(onlineShopOrderQueue).to(onlineShopExchange).with(ORDER_ROUTING_KEY);
    }

    @Bean
    public Binding onlineShopUserBinding(Queue onlineShopUserQueue, TopicExchange onlineShopExchange)
    {
        return BindingBuilder.bind(onlineShopUserQueue).to(onlineShopExchange).with(USER_ROUTING_KEY);
    }

    /**
     * since we use object to send message to rabbitMQ, therefore
     * we need to convert it
     */
    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * With this rabbit template we can publish and consume the message
     */
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory)
    {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
