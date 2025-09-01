package com.dreampass.test.controller.model;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push")
public class PushController {

    private final SimpMessagingTemplate messagingTemplate;

    public PushController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public String push(@RequestParam String msg) {
        messagingTemplate.convertAndSend("/topic/push", msg);
        return "推送成功: " + msg;
    }
}
