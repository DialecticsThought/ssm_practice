package liu.servlet;

import liu.config.AppConfig;
import liu.config.RootConifg;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/*
web容器启动的时候 创建对象 调用方法初始化容器 以及前端控制器
 * web容器 扫描 controller view-resolver视图解析器
 * 根容器 扫描业务逻辑组件 service repository 数据源DataSource等
 * */
public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    //获得根容器的配置类 Spring的配置文件 父容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConifg.class};
    }
    //获取web容器的配置类 SpringMVC配置文件 也就是子容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }
    //获取dispatcherServlet的映射信息
    @Override
    protected String[] getServletMappings() {
        // "/"拦截所有请求 包括静态资源 除了*.jsp
        // "/*" 拦截包括jsp 的所有
        //因为jsp是tomcat的jsp引擎解析的
        return new String[]{"/"};
    }
}
