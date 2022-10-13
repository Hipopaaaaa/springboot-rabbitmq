package com.ohj.springbootrabbitmq.controller;

import com.ohj.springbootrabbitmq.config.PriorityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//生产者
@Slf4j
@RestController
@RequestMapping("/priority")
public class PriorityController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void receivePriority(@PathVariable String message){

        log.info("发送100条消息");
        int count=1;

        for (int i = 0; i < 100; i++) {
            if(i>90){
                //当第90到100条消息的优先级为 1-10
                int finalCount = count;
                rabbitTemplate.convertAndSend(PriorityConfig.EXCHANGE,PriorityConfig.ROUTINGKEY,message+i, msg->{
                    msg.getMessageProperties().setPriority(new Integer(finalCount));
                    return msg;
                });
            }else {
                rabbitTemplate.convertAndSend(PriorityConfig.EXCHANGE,PriorityConfig.ROUTINGKEY,message+i);
            }
        }
    }
}
