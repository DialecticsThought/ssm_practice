package ljh.test;

import ljh.bean.Book;
import ljh.bean.Person;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class IOCTest {


    /*
    * 单例bean的生命周期
    *       （容器启动）构造器-->初始化方法--->销毁方法
    * */
    @Test
    public void test01(){
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");
        System.out.println("容器要关闭了");
        ioc.close();
    }
    /*
    *多例bean的生命周期
    *       获取bean（getBean）构造器-->初始化方法--->容器关闭不会调用bean的销毁方法
    * */

    /*
    *
    * 单例bean的生命周期
    *       （容器启动）构造器-->后置处理器before --->初始化方法---->后置处理器after --->销毁方法
    *
    * 无论有没有初始化方法 后置处理器都会默认其有初始化方法 还会继续工作☆☆☆☆☆☆
    * */
    @Test
    public void test02(){
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");
        Book book01 = ioc.getBean("book01", Book.class);
        System.out.println("容器要关闭了");
        ioc.close();
    }

    @Test
    public void test03() throws SQLException {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("ioc2.xml");
        DataSource dataSource = ioc.getBean("dataSource", DataSource.class);
        System.out.println(dataSource.getConnection());

    }

    @Test
    public void test04()  {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("ioc03.xml");
        Person person01 = ioc.getBean("person01", Person.class);
        Person person02 = ioc.getBean("person02", Person.class);
        System.out.println(person01.toString());
        System.out.println(person02.toString());
    }
}
