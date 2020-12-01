package com.zyfgoup.exception;

import com.zyfgoup.entity.ErrorCode;

import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/12/1 11:12
 * @Description
 */
public class ResourceNotFoundException extends BaseException {

    //构建一个异常类只需要传入一个简单的数据即可  异常码是写死的了
    public ResourceNotFoundException(Map<String,Object> data){
        super(ErrorCode.RESOURCE_NOT_FOUND,data);
    }
}
