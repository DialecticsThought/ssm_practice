package liu.controller;

import liu.bean.Employee;
import liu.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class EmployeeController {
    //注入的是dao层的bean对象
    @Autowired//默认先byType 在byName
    private EmployeeDao employeeDao;

    @GetMapping("/employee")
    public String getAllEmployee(Model model){
        Collection<Employee> all = employeeDao.getAll();
        //放在request域
        model.addAttribute("employeeList",all);
        return "employee_list";
    }

    @RequestMapping(value = "/employee/{id}",method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        //跳转到列表页面 但不能直接访问 因为上面/employee请求控制
        //删除之后的请求和原先的请求没有关系 用重定向 并且是get请求
        return "redirect:/employee";
    }
    //用pojo类接收对象
    //添加新的用户
    @RequestMapping(value = "/employee",method = RequestMethod.POST)
    public String addEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/employee";
    }
    /*
    * 更新用户的第一步
    *  查到要更新的用户的具体信息 然后回显到employee_update.html页面
    *
    * */
    @RequestMapping(value = "/employee/{id}",method = RequestMethod.GET)
    public String getEmployeeById(@PathVariable("id") Integer id,Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee",employee);
        return "employee_update";
    }
    /*
    * 更新用户第二部
    *
    * */
    @RequestMapping(value = "/employee",method = RequestMethod.PUT)
    public String updateEmployee(Employee employee){
        //把原来的值覆盖掉
        employeeDao.save(employee);
        return "redirect:/employee";
    }
}
