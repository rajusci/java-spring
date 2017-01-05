package com.inspirenetz.api.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by fayiz on 31/07/15.
 */
@Configuration
@PropertySource(value = "classpath:email.properties")
public class NotificationTestConfig {

    @Value("${email.hostname}")
    protected String emailHost;

    @Value("${email.port}")
    protected int emailPort;

    @Value("${email.username}")
    protected String emailUserName;

    @Value("${email.password}")
    protected  String emailPassword;

    @Value("${email.from}")
    protected  String emailFrom;

    @Value("${email.prop.mail.transport.protocol}")
    protected  String mailTransportProtocol;

    @Value("${email.prop.smtp.starttls.enable}")
    protected  String sslEnabled;

    @Value("${email.prop.smtp.auth}")
    protected  String smtpAuthEnabled;

    @Value("${email.prop.smtp.socketFactory.class}")
    protected  String smtpSocketFactoryClass;

    @Value("${email.mail.debug}")
    protected  String mailDebug;

    @Bean
    public JavaMailSenderImpl mailSender() {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(emailHost);
        javaMailSender.setPort(emailPort);
        javaMailSender.setUsername(emailUserName);
        javaMailSender.setPassword(emailPassword);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {

        Properties properties = new Properties();

        properties.setProperty("mail.transport.protocol", mailTransportProtocol);
        properties.setProperty("mail.smtp.auth", smtpAuthEnabled);
        properties.setProperty("mail.smtp.starttls.enable", sslEnabled);
        properties.setProperty("mail.debug", mailDebug);
        properties.setProperty("mail.smtp.socketFactory.class",smtpSocketFactoryClass);

        return properties;
    }

    /*@Bean
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
    }*/


}
