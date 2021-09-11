package liu.test;

import liu.bean.Boss;
import liu.bean.Car;
import liu.bean.Color;
import liu.config.MainConfigOfAutowired;
import liu.config.MainConfigOfPropertyValues;
import liu.dao.BookDao;
import liu.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.awt.*;

public class IOCTest_Autowired {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);

        System.out.println("容器创建完成。。。。");

        BookService bean = applicationContext.getBean(BookService.class);

        System.out.println(bean);

/*
        BookDao bean1 = applicationContext.getBean(BookDao.class);

        System.out.println(bean1);
*/

        Boss boss = applicationContext.getBean(Boss.class);

        System.out.println(boss);

        Car car = applicationContext.getBean(Car.class);

        System.out.println(car);

       Color color = applicationContext.getBean(Color.class);

        System.out.println(color);

        applicationContext.close();
    }
}
