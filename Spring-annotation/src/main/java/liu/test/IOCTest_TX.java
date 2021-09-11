package liu.test;

import liu.aop.MathCalculator;
import liu.config.MainConfigOfAOP;
import liu.tx.Txconfig;
import liu.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_TX {

    //1.使用命令行参数的方法 -Dspring.profiles.active=test
    //利用代码的方式
    @Test
    public void test01() {
        //1.创建一个容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Txconfig.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String name:beanDefinitionNames){
            System.out.println(name);
        }

        UserService bean = (UserService) applicationContext.getBean(UserService.class);

        bean.insertUser();

        applicationContext.close();
    }
}
