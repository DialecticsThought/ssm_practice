package liu.config;

import liu.interceptor.FirstInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/*
* 一定要在pom.xml <packaging>war</packaging>
*
* */
//只要controller
//useDefaultFilters = false禁用默认的过滤规则  includeFilters要添加上
@ComponentScan(value = "liu",
        includeFilters =
                {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})}
,useDefaultFilters = false)
@EnableWebMvc//开启mvc注解驱动
public class AppConfig implements WebMvcConfigurer {

    //开启静态资源<mvc:default-servlet-handler/>
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //配置视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //默认是所有的页面都是从/WEB-INF/xxx.jsp
        registry.jsp();
        //可以自定义
        registry.jsp("/WEB-INF/view",".jsp");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // "/**"表示拦截任意路径
        registry.addInterceptor(new FirstInterceptor()).addPathPatterns("/**");
    }
}
