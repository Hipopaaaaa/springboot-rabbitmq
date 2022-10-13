package com.ohj.springbootrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DelayQueueConsumer {

    //监听消息
    @RabbitListener(queues = "delayed.queue")
    public void receiveDelayQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},接收到来自延迟队列的消息：{}",new Date().toString(),msg);
    }
}
