package liu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/testThymeleafView")
    public String testThymeleafView(){
        return "success";
    }
    //地址栏是/testForward 显示的是/testThymeleafView
    @RequestMapping("/testForward")
    public String testForward(){
        //转发到/testThymeleafView请求中
        //会创建2个视图 一个是 把forward:截取掉 剩余的部分通过转发 创建的是转发视图是InternalResourceView
        //另一个是ThymeleafView
        //转发不能直接访问某一个html 必须通过服务器访问 也就是转发访问 要么 是跳转到某一个html的请求 要么直接在return 里面加上html的视图名 eg:success
        return "forward:/testThymeleafView/";
    }
    /*
    * 转发是一次浏览器请求还有一次服务器内部  重定向浏览器发送两次请求 第一次servlet第二次重定向的地址
      转发后的地址还是第一次浏览器请求的地址 重定向第二次重定向的地址
      转发的request对象是同一个 所以转发可以获取请求域中的数据 重定向 因为二次请求 对应不同的request对象  转
      发能访问WEB-INF的资源（只能通过服务器内部访问） 重定向不能访问
      转发不能跨域 因为转发发生在服务器内部 所以只能访问服务器内部的资源 重定向是浏览器的二次请求 所以能访问其他资源 比如说访问百度
    * */
    //地址栏显示的/testThymeleafView
    @RequestMapping("/testRedirect")
    public String testDirect(){
        return "redirect:/testThymeleafView";
    }
}
