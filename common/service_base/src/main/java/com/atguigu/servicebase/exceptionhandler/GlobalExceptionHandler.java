package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j//日志输出到文件
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("有吴！");
    }

    //特定异常  除数不能为0
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("ArithmeticException！");
    }


    //自定义异常
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e){
        log.error(e.getMsg());//日志输出到文件
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
