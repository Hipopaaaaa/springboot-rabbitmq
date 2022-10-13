package com.ohj.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriorityConfig {

    //交换机
    public static final String EXCHANGE="exchange";
    //队列
    public static final String PRIORITY_QUEUE="priority.queue";
    //routingkey
    public static final String ROUTINGKEY="key1";

    //声明
    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE);

    }
    @Bean
    public Queue priorityQueue(){
        //设置队列优先级最大为10
        return QueueBuilder.durable(PRIORITY_QUEUE).maxPriority(10).build();
        //return QueueBuilder.durable(PRIORITY_QUEUE).lazy().build();
    }
    //绑定
    @Bean
    public Binding binding(@Qualifier("exchange") DirectExchange exchange,
                           @Qualifier("priorityQueue") Queue priorityQueue){
        return BindingBuilder.bind(priorityQueue).to(exchange).with(ROUTINGKEY);
    }


}
