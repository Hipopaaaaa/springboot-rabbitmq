package com.ohj.springbootrabbitmq.consumer;

import com.ohj.springbootrabbitmq.config.PriorityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
//消费者
@Slf4j
@Component
public class Consumer {
    @RabbitListener(queues = PriorityConfig.PRIORITY_QUEUE)
    public void receiverPriority(Message message){
        String msg = new String(message.getBody());
        log.error("接收到消息：{}",msg);
    }
}
