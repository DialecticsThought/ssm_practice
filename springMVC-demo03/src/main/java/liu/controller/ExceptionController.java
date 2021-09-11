package liu.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    //@ExceptionHandler用于设置所标识方法处理的异常
    @ExceptionHandler(value ={ArithmeticException.class})
    //ex表示当前请求的异常
    public String testException(Exception ex, Model model){
        model.addAttribute("ex",ex);
        return "error";
    }
}
