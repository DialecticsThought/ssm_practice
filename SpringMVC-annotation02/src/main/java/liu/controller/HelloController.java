package liu.controller;

import liu.service.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @Autowired
    ServiceTest serviceTest;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        String s = serviceTest.sayHello("tomcat....");
        return s;
    }
}
