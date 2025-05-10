package com.hukai.codingtest.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private int code;
    private String message;
    private T data;

    public static <T> Response<T> success(T data) {
    	return new Response<>(200, "success", data);
    }

    public static <T> Response<T> error(int code, String message) {
    	return new Response<>(code, message, null);
    }
}
