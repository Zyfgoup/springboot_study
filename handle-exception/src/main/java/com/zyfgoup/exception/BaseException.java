package com.zyfgoup.exception;

import com.zyfgoup.entity.ErrorCode;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/12/1 11:04
 * @Description
 * 自定义的系统的所有异常的父类
 */
public class BaseException  extends  RuntimeException{
    private ErrorCode error;
    private final HashMap<String, Object> data = new HashMap<>();

    public BaseException(ErrorCode error, Map<String,Object> map){
        super(error.getMessage());
        this.error = error;
        if(!ObjectUtils.isEmpty(map)){
            this.data.putAll(map);
        }
    }

    protected BaseException(ErrorCode error,Map<String, Object> data,Throwable cause){
        super(error.getMessage(),cause);
        this.error = error;
        if(!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }



    public ErrorCode getError() {
        return error;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
