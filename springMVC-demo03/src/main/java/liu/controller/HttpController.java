package liu.controller;

import liu.bean.User;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HttpController {
    @PostMapping("/testRequestBody")
    //需要在控制器方法设置一个形参，使用@RequestBody进行标识，当
    //前请求的请求体就会为当前注解所标识的形参赋值
    public String testRequestBody(@RequestBody String requestBody){
        System.out.println("requestBody: "+requestBody);
        return "success";
    }
    //RequestEntity封装请求报文的一种类型，需要在控制器方法的形参中设置该类型的形参，当前请求的
    //请求报文就会赋值给该形参，可以通过getHeaders()获取请求头信息，通过getBody()获取请求体信息
    @RequestMapping("/testRequestEntity")
    public String  testRequestEntity(RequestEntity<String> requestEntity){
        System.out.println("请求头："+requestEntity.getHeaders());
        System.out.println("请求体：" +requestEntity.getBody());
        return "success";
    }
    //响应给浏览器一个完整的网页（转发/重定向）/数据
    @RequestMapping("/testResponse")
    public void testResponse(HttpServletResponse response) throws IOException {
        //把print的内容作为响应报文的响应体丢给浏览器
        //响应体是什么 在浏览器看到的就是什么
        response.getWriter().print("hello,repsonse");
    }

    @RequestMapping("/testResponseBody")
    @ResponseBody
    public String testResponseBody(){
        //原先返回的是视图名称 因为@ResponseBody 现在返回的是响应体的内容
        return "success";
    }
    @RequestMapping("/testResponseUser")
    @ResponseBody
    public User testResponseUser(){
        /*
        * 浏览器不能直接接受对象 用json格式（一种是json对象 另一种是json数组）
        * json是一种数据交互格式
        * 实体类对象转换后是json对象
        * map转换后json对象
        * list转换后是json数组
        * */
        return new User(1001,"admin","123456",24,"admin@qq.com");
    }

    @RequestMapping("testAxios")
    @ResponseBody
    public String textAxios(String username,String password){
        System.out.println(username+","+password);
        return "hello,axios";
    }
}
