package com.ohj.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class TtlQueueConfig {
    //普通交换机
    public static final String X_EXCHANGE="X";
    //死信交换机
    public static final String Y_DEAD_LETTER_EXCHANGE="Y";
    //普通队列
    public static final String QUEUE_A="QA";
    public static final String QUEUE_B="QB";
    //死信队列
    public static final String DEAD_LETTER_QUEUE="QD";

    //声明xExchange交换机
    @Bean
    public DirectExchange xExchange(){
        return  new DirectExchange(X_EXCHANGE);
    }
    //声明yExchange交换机
    @Bean
    public DirectExchange yExchange(){
        return  new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明普通队列QA ttl为10s
    @Bean
    public Queue queueA(){
        //绑定死信交换机，死信交换机的routingKey，设置ttl
        return QueueBuilder.durable(QUEUE_A).
                deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").ttl(10000).build();
    }

    //声明普通队列QB ttl为40s
    @Bean
    public Queue queueB(){
        return QueueBuilder.durable(QUEUE_B).
                deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").ttl(40000).build();
    }
    //声明死信队列
    @Bean
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA, @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }
    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}
