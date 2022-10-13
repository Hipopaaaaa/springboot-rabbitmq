package com.ohj.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {

    //交换机
    public static final String CONFIRM_EXCHANGE="confirm.exchange";
    //队列
    public static final String CONFIRM_QUEUE="confirm.queue";
    //routingkey
    public static final String ROUTINGKEY="key1";

    //备份交换机
    public static final String BACKUP_EXCHANGE="backup.exchange";
    //报警队列
    public static final String WARNING_QUEUE="warning.queue";
    //备份队列
    public static final String BACKUP_QUEUE="backup.queue";

    //声明
    @Bean
    public DirectExchange confirmExchange(){
        //确认交换机与备份交换机进行绑定
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).durable(true).
                alternate(BACKUP_EXCHANGE).build();
    }

    @Bean
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE);
    }
    @Bean
    public Queue confirmQueue(){
        return new Queue(CONFIRM_QUEUE);
    }
    @Bean
    public Queue warningQueue(){
        return new Queue(WARNING_QUEUE);
    }
    @Bean
    public Queue backupQueue(){
        return new Queue(BACKUP_QUEUE);
    }

    //绑定
    @Bean
    public Binding confirmQueueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                     @Qualifier("confirmExchange") DirectExchange confirmExchange){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(ROUTINGKEY);
    }
    @Bean
    public Binding backupQueueBindingBackupExchange(@Qualifier("backupQueue") Queue backupQueue,
                                                    @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }
    @Bean
    public Binding warningQueueBindingBackupExchange(@Qualifier("warningQueue") Queue warningQueue,
                                                     @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
