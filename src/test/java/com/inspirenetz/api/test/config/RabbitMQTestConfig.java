package com.inspirenetz.api.test.config;

import com.inspirenetz.api.core.amqp.RMQSaleDataReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * Created by sandheepgr on 14/10/14.
 */
@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
public class RabbitMQTestConfig {

    @Value("${rmq.hostname}")
    protected String rmqHost;

    @Value("${rmq.username}")
    protected String rmqUsername;

    @Value("${rmq.password}")
    protected String rmqPassword;

    @Value("${rmq.fastdataqueuename}")
    protected  String fastDataQueueName;

    @Value("${rmq.batchfilequeuename}")
    protected  String batchFileQueueName;

    @Value("${rmq.fastdataexchange}")
    protected  String fastdataExchange;

    @Value("${rmq.batchfileexchange}")
    protected  String batchFileExchange;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rmqHost);
        connectionFactory.setUsername(rmqUsername);
        connectionFactory.setPassword(rmqPassword);
        return connectionFactory;
    }

    @Bean
    Queue fastDataQueue() {
        return new Queue(fastDataQueueName, false);
    }


    @Bean
    Queue batchFileQueue() {
        return new Queue(batchFileQueueName, false);
    }

    @Bean
    TopicExchange fastdataExchange() {
        return new TopicExchange(fastdataExchange);
    }

    @Bean
    TopicExchange batchFileExchange() {
        return new TopicExchange(batchFileExchange);
    }

    @Bean
    Binding fastdataBinding(Queue fastDataQueue, TopicExchange fastdataExchange) {
        return BindingBuilder.bind(fastDataQueue).to(fastdataExchange).with(fastDataQueueName);
    }


    @Bean
    Binding batchFileBinding(Queue batchFileQueue, TopicExchange batchFileExchange) {
        return BindingBuilder.bind(batchFileQueue).to(batchFileExchange).with(batchFileQueueName);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(fastDataQueueName, batchFileQueueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }


    @Bean
    RMQSaleDataReceiver rmqSaleDataReceiver() {
        return new RMQSaleDataReceiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RMQSaleDataReceiver rmqSaleDataReceiver) {
        return new MessageListenerAdapter(rmqSaleDataReceiver, "readSale");
    }


    @Bean
    public AmqpAdmin amqpAdmin() {

        // Create the rabbitAdmin class
        RabbitAdmin admin = new RabbitAdmin(connectionFactory());

        // Return the admin object
        return admin;

    }



    @Bean
    public RabbitTemplate rabbitTemplate() {

        // Create the RabbiTemplate from the connection factory
        RabbitTemplate template = new RabbitTemplate(connectionFactory());

        return template;
    }


}
