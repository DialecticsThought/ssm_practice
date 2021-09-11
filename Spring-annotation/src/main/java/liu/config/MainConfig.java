package liu.config;

import liu.bean.Person;
import liu.dao.BookDao;
import liu.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

//配置类 == 配置文件
@Configuration//告诉spring这个类是配置类
/*
*   <!--只要标注了@Controller @Service @Repository @Component 就会被自动扫描到容器中 -->
    <context:component-scan base-package="liu" use-default-filters="true"></context:component-scan>
    * 写排除规则 excludeFilters的value是一个数组 数组里面写Filter
    *  Filter里面写过滤的东西
    * 可以写按照类ASSIGNABLE_TYPE,classes = {BookDao.class}
    * 按照注解FilterType.ANNOTATION,classes ={Controller.class, Service.class}
    * use-default-filters="true"表示禁用默认规则
* */
/*
@ComponentScan(value = "liu",
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter()},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes ={Controller.class, Service.class})})
*/
//@ComponentScans的value是一个数组 数组每个值都是一个@ComponentScan
@ComponentScans(value =
        {@ComponentScan(value = "liu",
            useDefaultFilters = false,
            includeFilters = {
               /* @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {BookDao.class}),*/
                @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class})
            }/*,
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION,classes ={Controller.class, Service.class})
            }*/
        )
        }
)
public class MainConfig {
    /*
    *<bean id="person" class="liu.bean.Person">
        <property name="age" value="20"></property>
        <property name="name" value="ab"></property>
    </bean>
    *
    * */
    @Bean("person01")//给容器注册一个bean 类型是返回值得类型 id默认是用方法名的id/@Bean注解上写名字
    public Person person(){
        return new Person("ab",20);
    }
}
