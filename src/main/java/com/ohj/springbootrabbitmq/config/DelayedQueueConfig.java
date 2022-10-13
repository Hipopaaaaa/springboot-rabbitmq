package com.ohj.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedQueueConfig {

    //交换机
    public static final String DELAYED_EXCHANGE="delayed.exchange";
    //队列
    public static final String DELAYED_QUEUE="delayed.queue";

    //声明延迟交换机，自定义一个交换机
    @Bean
    public CustomExchange delayedExchange(){
        Map<String, Object> arguments =new HashMap<>();
        //设置延迟交换机与队列的交互类型（direct、topic、fanout）
        arguments.put("x-delayed-type","direct");
        /**
         * 1.第一个参数： 交换机名
         * 2.第二个参数： 交换机类型
         * 3.第三个参数： 交换机是否需要持久化
         * 4.第四个参数： 交换机是否需要自动删除
         * 4.第五个参数： 参数设置
         */
        return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",false,true,arguments);
    }

    //声明队列
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE);
    }

    //绑定
    @Bean
    public Binding queueBindingExchange(@Qualifier("delayedQueue") Queue delayedQueue,
                                        @Qualifier("delayedExchange") CustomExchange delayedExchange){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with("delayed.routingkey").noargs();
    }

}
