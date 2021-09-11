package liu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class ScopeController {
    /*
     * 控制器方法中创建相应的形参 形参向请求中共享数据  ModelandView需要在控制器方法中创建对象 让控制器方法返回ModelandView才有效果
     * 因为有两个功能 一个封装数据 一个返回视图信息  创建的对象 返回给前端控制器来解析
     *
     *BindingAwareModelMap extends ExtendedModelMap
     *ExtendedModelMap extends ModelMap implements Model
     *ModelMap extends LinkedHashMap<String, Object>

     * request请求一开始是经过dispatcherServlet解析 所以debug的时候 看方法栈doDispatch
     * */

    //使用servletAPI向request域对象共享数据
    @RequestMapping("/testRequestByServletAPI")
    public String testRequestByServletAPI(HttpServletRequest request){
        request.setAttribute("testRequestScope","hello,servletAPI");
        //只能转发 不能重定向到WEB-INF文件夹下的资源
        return "success";
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        /*** ModelAndView有Model和View的功能
        * Model主要用于向请求域共享数据
        * View主要用于设置视图，实现页面跳转 */
        ModelAndView mav = new ModelAndView();
        //向请求域共享数据
        mav.addObject("testScope", "hello,ModelAndView");
        //设置视图，实现页面跳转
        mav.setViewName("success");
        return mav;
    }

    @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testRequestScope","hello,model");
        System.out.println(model.getClass().getName());
        return "success";
    }
    @RequestMapping("/testMap")
    public String testModel(Map<String,Object> map){
        map.put("testRequestScope","hello,map");
        System.out.println(map.getClass().getName());
        return "success";
    }
    //public class ModelMap extends LinkedHashMap<String, Object>
    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap map){
        map.put("testRequestScope","hello,ModelMap");
        System.out.println(map.getClass().getName());
        return "success";
    }
    @RequestMapping("testSession")
    public String testSession(HttpSession session){
        session.setAttribute("testSessionScope","hello,session");
        return "success";
    }
    //servletContext代表的是整个application的范围 在服务器启动时创建
    @RequestMapping("/tesstApplication")
    public String testApplication(HttpSession session){
        ServletContext application = session.getServletContext();
        application.setAttribute("testApplicationScope","hello,application");
        return "success";
    }
}
