package com.zyfgoup.entity;

import com.zyfgoup.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/12/1 10:55
 * @Description
 * 返回给客户端的异常对象
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class ErrorResponse {
    private int code;
    private int status;
    private String message;
    private String path;
    private Instant timestamp;
    private HashMap<String, Object> data = new HashMap<>();


    //调用下面的构造方法  时间戳自动赋值
    //传入自定义的异常类 和 当前请求的资源路径
    public ErrorResponse(BaseException ex, String path){
        this(ex.getError().getCode(),ex.getError().getStatus().value(),ex.getError().getMessage()
        ,path,ex.getData());
    }


    public ErrorResponse(int code, int status, String message, String path, Map<String,Object> map){
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        if(!ObjectUtils.isEmpty(map)){
            this.data.putAll(map);
        }
    }

}
