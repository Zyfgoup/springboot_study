package com.zyfgoup.handler;

import com.zyfgoup.controller.ExcepetionController;
import com.zyfgoup.entity.ErrorResponse;
import com.zyfgoup.exception.BaseException;
import com.zyfgoup.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Zyfgoup
 * @Date 2020/12/1 11:15
 * @Description
 * 全局异常处理类
 * @RestControllerAdvive处理全部Controller 或者指定某些Controller
 * = ControllerAdvice + ResponseBody
 */

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 其实只需要这个方法即可 处理自定义的所有异常
     * 但是如果更颗粒的定义了某个异常的处理方法 会走最匹配的
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleAppException(BaseException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex,request.getRequestURI());
        return new ResponseEntity<>(errorResponse,new HttpHeaders(),ex.getError().getStatus());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex,request.getRequestURI());
        return errorResponse;
    }



}
