package com.dreampass.test.controller.model;

import lombok.Data;

import java.util.List;

@Data
public class ListOrderRequest {

    private List<String> statusList;

    private int size;

    private int page;
}
