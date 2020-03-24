package com.wl.msnotify.util;

import com.wl.msnotify.enums.ResultCodeEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultUtil {
    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    // 构造器私有
    private ResultUtil() {
    }

    // 通用返回成功
    public static ResultUtil ok() {
        ResultUtil r = new ResultUtil();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    // 通用返回失败，未知错误
    public static ResultUtil error() {
        ResultUtil r = new ResultUtil();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }

    // 设置结果，形参为结果枚举
    public static ResultUtil setResult(ResultCodeEnum result) {
        ResultUtil r = new ResultUtil();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    // 自定义返回数据
    public ResultUtil data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    // 通用设置data
    public ResultUtil data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    // 自定义状态信息
    public ResultUtil message(String message) {
        this.setMessage(message);
        return this;
    }

    // 自定义状态码
    public ResultUtil code(Integer code) {
        this.setCode(code);
        return this;
    }

    // 自定义返回结果
    public ResultUtil success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}