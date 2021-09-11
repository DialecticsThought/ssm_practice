package liu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

//把他作为ioc的一个组件 通过注解+扫描 让它成为控制器
//才可以让这个类的方法作为控制器方法 来接受和响应请求
@Controller
public class ParamController {

    /*
    *http://localhost:8080/springMVC_demo02/param访问
    * */

    @RequestMapping("/testServletAPI")
    //如果dispatcherServlet检测到了HttpServletRequest对象 就会把 当前请求赋值给形参request
    public String testServletAPI(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        //浏览器 开启->关闭
        //第一次的话 会生成一个httpsession对象(并存到服务器的map集合中 是map集合的值)和对应的id(是map集合的键)
        // id会通过cookie的形式发送到客户端（在响应体里面）除了第一次 之后cookie存在于请求头中
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        System.out.println(username);
        return "success";
    }

    @RequestMapping("/testParam")
    public String testParam(@RequestParam("username") String username,
                            @RequestParam(value = "password",required = false,defaultValue = "我是你爹") String password,
                            @RequestParam("hobby") String[] hobby
                            //获取host的ip地址
                           , @RequestHeader("Host") String host
                            , @CookieValue(value = "JSEESIONID",required = false) String jSessionid){
        //如果请求参数出现了多个同名的请求参数 可以在控制器方法的形参位置设置字符串类型/字符串数组接收此参数
        //若使用字符串数组类型的形参，此参数的数组中包含了每一个数据
        //若使用字符串类型的形参，此参数的值为每个数据中间使用逗号拼接的结果
        System.out.println(username+","+password+","+ Arrays.toString(hobby)+","+host+","+jSessionid);
        return "success";
    }

    @RequestMapping("/testPOJO")
    public String testPOJO(User user){
        System.out.println(user.toString());
        return "success";
    }
    //在设置前就获得参数 没有任何参数 只有post才会出现乱码  get请求的乱码是tomcat解决的 conf目录下的server.xml
    //注册servlet的时候<load-on-startup>1</load-on-startup>说明服务器启动的时候已经加载servlet了 要在加载servlet之前 加载编码
    //因为filter 执行先于servlet 所以用 filter来在servlet处理参数之前编码
}
