package com.dreampass.resource.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum ResourceTypeEnum {

    API("API", "接口"),
    MENU("MENU", "菜单"),
    BUTTON("BUTTON", "按钮"),
    PAGE("PAGE", "页面");
    // API / MENU / BUTTON / PAGE

    private final String code;

    private final String description;

    ResourceTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ResourceTypeEnum findByCode(String code) {
        for (ResourceTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
