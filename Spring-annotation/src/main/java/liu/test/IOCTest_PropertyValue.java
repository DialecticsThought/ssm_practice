package liu.test;

import liu.config.MainConfigLifetCycle;
import liu.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class IOCTest_PropertyValue {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);

        System.out.println("容器创建完成。。。。");

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String name:beanDefinitionNames){
            System.out.println(name);
        }

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 因为这些配置文件的变量都存在environment变量里面
        String property = environment.getProperty("person.nickname");

        System.out.println(property);

        applicationContext.close();
    }
}
