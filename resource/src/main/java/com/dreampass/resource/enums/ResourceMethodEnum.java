package com.dreampass.resource.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum ResourceMethodEnum {

    UNDEFINED("UNDEFINED", "未定义"),
    POST("POST", "创建"),
    DELETE("DELETE", "删除"),
    UPDATE("UPDATE", "更新"),
    GET("GET", "查询");

    private final String code;

    private final String description;

    ResourceMethodEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ResourceMethodEnum findByCode(String code) {
        for (ResourceMethodEnum method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        return null;
    }
}
