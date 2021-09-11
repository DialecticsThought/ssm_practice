package liu.test;

import liu.bean.Blue;
import liu.bean.ColorFactory;
import liu.bean.Person;
import liu.config.MainConfig;
import liu.config.MainConfig2;
import org.aspectj.tools.ajc.Main;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;
/*
* MainConfig.class
* MainConfig2.class
* */
public class IOCTest {
    @Test
    public void test01() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        //得到所有bean定义的名字
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    @Test
    public void test02() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        System.out.println("容器创建完成");
        //得到所有bean定义的名字
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

        Person person = (Person) annotationConfigApplicationContext.getBean("person");
        Person person1 = (Person) annotationConfigApplicationContext.getBean("person");

        System.out.println(person == person1);
    }
    /*
    * 测试condition注解
    *
    * */
    @Test
    public void test03() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        System.out.println("容器创建完成");

        String[] beanNamesForType = annotationConfigApplicationContext.getBeanNamesForType(Person.class);

        ConfigurableEnvironment environment = annotationConfigApplicationContext.getEnvironment();
        //获取操作系统的信息
        String property = environment.getProperty("os.name");

        System.out.println(property);

        for (String name : beanNamesForType) {
            System.out.println(name);
        }

        Map<String, Person> beansOfType = annotationConfigApplicationContext.getBeansOfType(Person.class);

        System.out.println(beansOfType);
    }

    @Test
    public void test04() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        System.out.println("容器创建完成");
        //得到所有bean定义的名字
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

/*        Blue bean = applicationContext.getBean(Blue.class);

        System.out.println(bean);*/
        //工厂bean获取的是调用getObject创建对象 那么类型是Color 而不是ColorFactory
        Object color1 = applicationContext.getBean("colorFactory");

        Object color2 = applicationContext.getBean("colorFactory");
        System.out.println("这个bean对象的类型是:" + color1.getClass());
        System.out.println("这个bean对象的类型是:" + color2.getClass());
        System.out.println(color1 == color2);

        Object colorFactory1 = applicationContext.getBean("&colorFactory");

        System.out.println("bean类型是："+colorFactory1.getClass());
    }
}
