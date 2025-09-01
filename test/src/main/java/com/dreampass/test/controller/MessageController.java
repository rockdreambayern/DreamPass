package com.dreampass.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MessageController {

    @MessageMapping("sendMsg")
    @SendTo("/topic/push")
    public String sendMsg(String msg) {
        log.info("收到前端消息:{}", msg);
        return "收到前端消息:" + msg;
    }
}
