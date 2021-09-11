package liu.config;

import liu.interceptor.TestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;
import java.util.Properties;


/*
* 代替SpringMVC配置文件
* 扫描组件
* 视图解析器
* view-controller
* default-servlet-handler
* mvc注解驱动
* 文件上传解析器
* 异常处理
* 拦截器
* */
@Configuration//将当前类表示为配置类
//<mvc:annotation-driven/>
@EnableWebMvc//开启mvc注解驱动
//<context:component-scan base-package="liu"></context:component-scan>
@ComponentScan("liu")//扫描组件
//配置不是bean对象的功能
public class WebConfig implements WebMvcConfigurer {
    /*
    * <!--
        tomcat服务器有个默认的web.xml范围是整个tomcat 每个工程项目有个web.xml 范围是整个工程项目
        tomcat的web.xml有个
        <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>/</url-pattern>
        </servlet-mapping>
        会和自己定义的dispatcherServlet起冲突 并失效
        dispatcherServlet会根据地址取控制器找到相应的请求映射 但是控制器没有写静态资源的相应的请求映射
    -->
    <mvc:default-servlet-handler/>
    * */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TestInterceptor testInterceptor = new TestInterceptor();
        /*
        * <!--"/*"拦截所有只包括上下文路径下面的一层目录 拦截所有层的目录的话 path="/**"-->
        *  <mvc:mapping path="/*"/>
        *  <!--不拦截“/”-->
        *  <mvc:exclude-mapping path="/"/>
        * */
        registry.addInterceptor(testInterceptor).excludePathPatterns("/").addPathPatterns("/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /*
         <mvc:view-controller path="/" view-name="index"></mvc:view-controller>
        * */
        registry.addViewController("hello").setViewName("hello");
    }

    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        return commonsMultipartResolver;
    }
    /*
    *   <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <!--当出现指定的异常 给予新的视图名称-->
        <property name="exceptionMappings">
            <props>
                <prop key="java.lang.ArithmeticException">error</prop>
            </props>
        </property>
        <!--出现异常 会把异常信息存到请求域中 value就是键-->
        <property name="exceptionAttribute" value="ex"></property>
        </bean>
    * */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("java.lang.ArithmeticException","error");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        simpleMappingExceptionResolver.setExceptionAttribute("exception");
    }

    /*
    * ViewResolver依赖于SpringTemplateEngine  SpringTemplateEngine依赖于WebApplicationContext
    * 所以倒着创建  依赖谁 先创建谁
    * */
    //配置生成模板解析器
    @Bean//让方法返回的对象是IOC容器中的bean对象
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过 WebApplicationContext 的方法获得
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
                webApplicationContext.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }
    //生成模板引擎并为模板引擎注入模板解析器
    @Bean //让方法返回的对象是IOC容器中的bean对象
    //IOC容器会根据形参的类型找到相应的值进行赋值 也就是自动装配
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }
    //生成视图解析器并未解析器注入模板引擎
     @Bean//让方法返回的对象是IOC容器中的bean对象
     //IOC容器会根据形参的类型找到相应的值进行赋值 也就是自动装配
     public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}
