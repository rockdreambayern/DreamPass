package com.dreampass.entity;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Builder
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;

    private String message;

    private T data;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failure(int code, String message) {
        return Result.<T>builder().code(code).message(message).build();
    }
}
