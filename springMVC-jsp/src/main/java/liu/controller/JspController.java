package liu.controller;


import liu.bean.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {
    //没有任何前缀和forward:为前缀是这个InternalResourceViewResolver
    @RequestMapping("/success")
    public String index(){
        return "success";
    }

    @RequestMapping("/testPOJO")
    public String testPOJO(Employee employee, Model model){
        System.out.println("员工信息"+employee.toString());

        model.addAttribute("employee",employee.toString());

        return "success";
    }
}
