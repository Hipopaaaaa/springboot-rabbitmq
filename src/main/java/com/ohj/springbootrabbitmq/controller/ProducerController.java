package com.ohj.springbootrabbitmq.controller;

import com.ohj.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message){
        //设置消息相关信息：消息编号、相关信息
        CorrelationData correlationData=new CorrelationData("1");
        CorrelationData correlationData2=new CorrelationData("2");

        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,ConfirmConfig.ROUTINGKEY,message,correlationData);
        //该消息发送失败，没有对应的routingkey
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,ConfirmConfig.ROUTINGKEY+"123",message+"null",correlationData2);
        log.info("发送消息：{}",message);
    }
}
