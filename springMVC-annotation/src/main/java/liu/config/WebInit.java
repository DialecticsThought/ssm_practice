package liu.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

//web工程的初始化类 用来代替web.xml
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    /*
    * 指定spring配置类
    * */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //创建一个Class类型的数组
        //return new Class[0];
        return new Class[]{SpringConfig.class};
    }
    /*
     * 指定springMVC配置类
     * */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }
    /*
     *指定DispatcherServlet的映射规则，即url-pattern
     * */
    @Override
    protected String[] getServletMappings() {
        /*
        <servlet-mapping>
        <servlet-name>springMVC</servlet-name>
        <!--设置springMVC的核心控制器所能处理的请求的请求路径
         /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
          但是/不能匹配.jsp请求路径的请求 -->
        <url-pattern>/</url-pattern>
        </servlet-mapping>
        *
        *
        * */
        return new String[]{"/"};
    }

    public WebInit() {
        super();
    }
    /*
    * <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!--设置请求的-->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <!--设置响应的-->
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    *
    *
    * */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter=new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceRequestEncoding(true);
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{encodingFilter,hiddenHttpMethodFilter};
    }
}
