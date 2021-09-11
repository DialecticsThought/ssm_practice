package liu.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/hello")
public class RequestController {
    //此时请求映射所映射的请求的请求路径为：/hello/success /hello/test
    //只支持 get 和post请求
    //请求必须携带username参数 且值必须是admin
    @RequestMapping(value = {"/success","/test"}
            ,method = {RequestMethod.GET,RequestMethod.POST}
            ,params = {"username=admin"})
    public String success(){
        return "success";
    }

    @GetMapping(value = "/success1")
    public String success1(){
        return "success";
    }

    @RequestMapping(value = "/testPut",method = RequestMethod.PUT)
    public String testPut(){
        return "success";
    }
    @RequestMapping(value = "/testParamsAndHeaders" ,params = {"username"}
    ,headers = {"Host=localhost:8080"})
    public String testParamsAndHeaders(){
        return "success";
    }
    //？ 表示任意的单个字符
    @RequestMapping("/a?a/testAnt")
    public String testAnt(){
        return "success";
    }

    //* 表示任意的多个字符
    @RequestMapping("/a*a/testAnt")
    public String testAnt1(){
        return "success";
    }
    //** 表示任意的单个/多个路径
    @RequestMapping("/**/testAnt")
    public String testAnt2(){
        return "success";
    }
    @RequestMapping("/testPath/{param1}")
    public String testPath(@PathVariable(value = "param1") Integer param1){
        System.out.println(param1);
        return "success";
    }
}
