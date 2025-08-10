package com.dreampass.test.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    INIT("INIT", "初始化"),
    HANDLE("HANDLE", "处理中"),
    COMPLETE("COMPLETE", "完成"),
    CANCELED("CANCELED", "取消");

    private final String code;

    private final String description;

    OrderStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderStatusEnum findByCode(String code) {
        for (OrderStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
