package com.dreampass.resource.entity;

import com.dreampass.resource.enums.ResourceMethodEnum;
import com.dreampass.resource.enums.ResourceTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResourceDo {

    private Long id;

    private Long tenantId;

    private String name;

    private ResourceTypeEnum type;

    private String path;

    private ResourceMethodEnum method;

    private Long parentId;     // 用于树结构（菜单、页面、按钮）

    private String description;

    private Boolean visible;   // 页面类资源是否可见（可选）

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
