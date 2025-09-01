package com.dreampass.test.controller.model;

import lombok.Data;

@Data
public class Message {

    private String clientId;

    private String appId;

    private String token;

    private String content;
}
