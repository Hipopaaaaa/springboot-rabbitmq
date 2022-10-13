package com.ohj.springbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        log.info("当前时间：{},发送的消息：{}",new Date().toString(),message);

        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s的队列："+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s的队列："+message);

    }

    @GetMapping("/sendMsg2/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间：{},发送了一条ttl为 {} 的消息：{}",new Date().toString(),ttlTime,message);

        rabbitTemplate.convertAndSend("X","XC","消息来自队列："+message,msg ->{
            //设置发送消息的ttl
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    @GetMapping("/sendMsg3/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message,@PathVariable Integer delayTime){
        log.info("当前时间：{},发送了一条ttl为 {} 的消息：{}",new Date().toString(),delayTime,message);

        //发送消息，并设置延迟时间
        rabbitTemplate.convertAndSend("delayed.exchange","delayed.routingkey","消息来自队列："+message,msg->{
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }




}
