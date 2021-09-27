package com.li.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {


    private int code;

    private String msg;

    private Object data;

    public static Result succ(Object data) {
        return Result.succ(200, "操作成功", data);
    }

    public static Result succ(int code, String msg, Object obj) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj);
        return result;
    }

    public static Result fail(String msg) {
        return Result.fail(400, msg, null);
    }

    public static Result fail(String msg, Object obj) {
        return Result.fail(400, msg, obj);
    }

    public static Result fail(int code, String msg, Object obj) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj);
        return result;
    }

}
