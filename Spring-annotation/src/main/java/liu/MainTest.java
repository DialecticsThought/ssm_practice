package liu;

import liu.bean.Person;
import liu.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        //基于类路径的
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");

        Person person = (Person) applicationContext.getBean("person");

        System.out.println(person);


        //基于注解 传的是配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);

        Person person01 = (Person) annotationConfigApplicationContext.getBean("person01");

        System.out.println(person01);
        String[] beanNamesForType = annotationConfigApplicationContext.getBeanNamesForType(Person.class);

        System.out.println(beanNamesForType);
    }
}
