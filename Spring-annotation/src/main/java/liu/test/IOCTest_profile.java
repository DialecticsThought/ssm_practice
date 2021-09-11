package liu.test;

import liu.bean.Blue;
import liu.config.MainConfigOfProfile;
import liu.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;

public class IOCTest_profile {

    //1.使用命令行参数的方法 -Dspring.profiles.active=test
    //利用代码的方式
    @Test
    public void test01() {
        //1.创建一个容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //2.设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("test","dev");
        //3.注册配置类
        applicationContext.register(MainConfigOfProfile.class);
        //4.启动刷新容器
        applicationContext.refresh();

        System.out.println("容器创建完成。。。。");

/*        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String name:beanDefinitionNames){
            System.out.println(name);
        }*/

        String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);

        for (String beanName : beanNamesForType) {

            System.out.println(beanName);
        }

        Blue bean = applicationContext.getBean(Blue.class);

        System.out.println(bean);

        applicationContext.close();
    }
}
