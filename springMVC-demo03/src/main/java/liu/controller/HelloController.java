package liu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//把他作为ioc的一个组件 通过注解+扫描 让它成为控制器
//才可以让这个类的方法作为控制器方法 来接受和响应请求
@Controller
public class HelloController {
    // @RequestMapping注解：处理请求和控制器方法之间的映射关系
    // @RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径
    // localhost:8080/springMVC/
    //通过上下文 跳转到index.ht
/*    @RequestMapping("/")
    public String index(){
        return "index";
    }*/

    @RequestMapping("/test_view")
    public String testView(){
        return "test_view";
    }
}
