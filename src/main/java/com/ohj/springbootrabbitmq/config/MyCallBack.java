package com.ohj.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    //需要把当前类注入到RabbitTemplate的ConfirmCallback里，RabbitTemplate才能调用这个类
    //注入
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }



    /**
     * 交换机确认回调方法
     *
     *
     * @param correlationData 保存回调消息的ID即相关信息
     * @param ack 交换机是否接收到消息
     * @param cause  失败的相关信息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        String id= correlationData!=null?correlationData.getId():"";

        if(ack){
            log.info("交换机已经接收到ID为：{}的消息",id);
        }else {
            log.info("交换机未收到ID为：{}的消息，原因：",id,cause);
        }
    }

    //当消息传递过程中失败时的回调
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息：{},被交换机：{}，退回，key：{},原因：{}",
                new String(returnedMessage.getMessage().getBody())
        ,returnedMessage.getExchange(),returnedMessage.getRoutingKey(),returnedMessage.getReplyText());
    }
}
