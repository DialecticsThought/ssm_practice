package liu.servlet;

import liu.service.HelloService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;
//容器启动的时候 会将@HandlesTypes指定的类型 以及子类 实现类 全部传递进来
@HandlesTypes(value = {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {
    /*
    * 应用启动的时候 会运行 onStartUp方法
    * ServletContext: 代表了一个web工程 对应了一个ServletContext
    * Set<Class<?>> c: @HandlesTypes指定的所有子类和实现类
    *
    * 使用ServletContext 注册web三大组件 必须在项目启动的时候添加
    *   1.使用ServletContext servletContext
    *   2.在filter中 方法contextInitialized(ServletContextEvent sce)执行的时候也可以初始化
    * */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        System.out.println("所有的类型");
        for (Class<?> type: c){
            System.out.println(type);
        }

        //注册组件 参数一是 servlet对象名字对应的是<servlet-name> 第二个是servlet类对应的是<servlet-class>
        ServletRegistration.Dynamic userServlet = servletContext.addServlet("userServlet", UserServlet.class);
        //配置servlet映射信息 <servlet-mapping>
        userServlet.addMapping("/user");
        //添加监听器
        servletContext.addListener(UserListener.class);
        //注册Filter  参数一对应<filter-name> 参数二对应<filter-class>
        FilterRegistration.Dynamic userFilter = servletContext.addFilter("userFilter", new UserFilter());
        //配置filter映射信息<filter-class>
        userFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

    }

}
