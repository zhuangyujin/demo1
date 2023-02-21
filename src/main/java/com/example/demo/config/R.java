package com.example.demo.config;

import lombok.Data;

@Data
public class R<T> {
    //编码,1代表成功,0和其他数字代表失败
    private int code;
    //数据
    private T data;
    //错误信息
    private String error;
    public static <A> R<A> success(A object)
    {
        R<A> r = new R();
        r.data =object;
        r.code=1;
        return r;
    }
    public static <A> R<A> error(String error)
    {
        R<A> r = new R();
        r.error=error;
        r.code=0;
        return r;
    }
}
