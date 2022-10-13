package com.ohj.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TtlQueueConfig02 {
    //交换机
    public static final String X_EXCHANGE="X";
    public static final String Y_DEAD_LETTER_EXCHANGE="Y";

    //队列
    public static final String QUEUE_C="QC";
    public static final String DEAD_LETTER_QUEUE="QD";
    //声明Direct交换机
    @Bean
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    @Bean
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明队列
    @Bean
    public Queue queueC(){
        return QueueBuilder.durable(QUEUE_C).
                deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").build();
    }
    //声明死信队列
    @Bean
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    //绑定
    @Bean
    public Binding queueCbindingX(@Qualifier("queueC") Queue queueC,@Qualifier("xExchange") DirectExchange xEchange){
        return BindingBuilder.bind(queueC).to(xEchange).with("XC");
    }
    @Bean
    public Binding queueDbindingY(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange") DirectExchange yEchange){
        return BindingBuilder.bind(queueD).to(yEchange).with("YD");
    }
}
