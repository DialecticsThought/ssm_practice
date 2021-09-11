package liu.config;


import liu.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/*
*     <!--要用到配置文件的话 就得指定位置-->
    <context:property-placeholder location="classpath:person.properties"/>
    *
    * 读取到配置文件的 k--v 保存到环境变量中 加载完外部的配置文件后 使用${}取出配置文件的值
* */
@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class MainConfigOfPropertyValues {
    /*
    * 测试 Person类的@Value
    * */
    @Bean
    public Person person(){
        return new Person();
    }
}
