package ljh.test;

import ljh.bean.AirPlane;
import ljh.bean.Book;
import ljh.bean.Car;
import ljh.bean.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class IOCTest {
    /*
    * 存在的几个问问题
    * 1）src源码包开始的路径 成为类路径的开始
    *       所有源码包里面的东西都会被合并到类路径下
    *           java /bin/
    *           web项目的类路径 /WEB-INF/classes
    *
    * 2）导入 commons-logging-1.1.3.jar
    * 3）先导包在创建配置文件
    *
    *
    * 细节：
    * 1）  FileSystemXmlApplicationContext (org.springframework.context.support) ioc配置文件在磁盘路径下面
        ClassPathXmlApplicationContext (org.springframework.context.support) ioc配置文件在类路径下面
    *
    * 2）applicationContext代表ioc容器
    *   给容器中注册一个组价 我们从容器中账号id拿到这个组件的对象？
    *   组件的创建工作 => 容器完成
    * 3) Person对象什么时候创建好
    *   容器中对象的创建在容器创建完成时一起创建
    *
    * 4）同一个组件在ioc是单例的 容器启动完成已经准备好了
    *
    * 5）容器中没有这个组件 会报异常 No bean named 'person03' available
    *
    * 6）ioc容器在创建这个组件对象的实收(property)会利用setter方法为javabean属性进行赋值
    *
    * 7）javabean的属性名是由什么决定的？getter、setter方法是属性名 set去掉后面那一串首字母小写就是属性名
    *   setGender ==> 为Gender属性赋值
    * */
    @Test
    public void test01(){
        //applicationContext代表ioc容器
        //当前应用的xml配置文件在classpath下 ClassPathXmlApplicationContext
        //ClassPathXmlApplicationContext( ) 方法是在其所在的目录中寻找 .xml 配置文件
        //根据Spring配置文件获得ioc容器对象
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");
        //容器帮我们创建好对象
        Object person01 = ioc.getBean("person01");
        Object person011 = ioc.getBean("person01");
        System.out.println(person011 == person01);
        System.out.println(person01);

        /*Object person03 = ioc.getBean("person03");
        System.out.println("==================");
        System.out.println(person03);*/
    }


    /*
    实验2：根据bean额理性从IOC容器中获取bean的实例
    如果ioc容器中这个类型的bean有多个 就会报错 ===>只能通过id
    No qualifying bean of type 'ljh.bean.Person' available: expected single matching bean but found 2: person01,person02

    * */
    @Test
    public void test02(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");

        /*Person bean = ioc.getBean(Person.class);
        System.out.println(bean);*/
        Person person01 = ioc.getBean("person01", Person.class);
        System.out.println(person01);
        //直接调用有参构造函数
        Person person03 = ioc.getBean("person03", Person.class);
        System.out.println(person03);
        /*
        *  <!--
        1)导入p名称空间
        2）写带前缀的标签属性-->
        * <!--p名称空间快速赋值-->
        *
        * */
        Person person05 = ioc.getBean("person05", Person.class);
        System.out.println(person05.toString());
    }
    /*实验4：正确的为各种属性赋值
    测试使用null值 默认引用类型就是null 基本类型就是默认值
    * */
    @Test
    public void test03(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc2.xml");
        Person person01 = ioc.getBean("person01", Person.class);
        System.out.println(person01.toString()+person01.getCar().getCarName());
        Car car01 = ioc.getBean("car01", Car.class);
        car01.setCarName("toyota");
        System.out.println(person01.toString()+person01.getCar().getCarName());
        /*因为person01的car是引用car01的*/
        System.out.println(person01.getCar() == car01);
    }

    @Test
    public void test04(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc2.xml");
        Person person02 = ioc.getBean("person02", Person.class);
        System.out.println(person02.toString()+person02.getCar().getCarName());
    }

    @Test
    public void test05(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc2.xml");
        Person person03 = ioc.getBean("person03", Person.class);
        System.out.println(person03.toString());
        List<Book> books = person03.getBooks();
        System.out.println(books);
        Map<String, Object> maps = person03.getMaps();
        System.out.println(maps);
        Properties properties = person03.getProperties();
        System.out.println(properties);
    }

    @Test
    public void test06(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc2.xml");
        Person person04 = ioc.getBean("person04", Person.class);
        System.out.println(person04.toString());
        Map<String, Object> maps = person04.getMaps();
        System.out.println(maps);
        //可以直接找到map通过id
        Object myMap = ioc.getBean("myMap");
        System.out.println(maps);
    }
    /*级联属性可以修改属性的属性*/
    @Test
    public void test07(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc2.xml");
        Person person05 = ioc.getBean("person05", Person.class);
        Car car01 = ioc.getBean("car01", Car.class);

        System.out.println(person05.toString());
        System.out.println("容器中的car"+car01);

        System.out.println("person的car"+person05.getCar());
    }

    @Test
    public void test08(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc2.xml");
        Person person07 = ioc.getBean("person07", Person.class);
        Person person06 = ioc.getBean("person06", Person.class);

        System.out.println(person06.toString());
        System.out.println(person07.toString());

    }
    @Test
    public void test09(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc3.xml");
        //看上去获取的是工厂 实际上获得飞机
        AirPlane airPlane02 = ioc.getBean("airPlane02", AirPlane.class);
        System.out.println(airPlane02);

    }

    @Test
    public void test10(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc3.xml");
        //看上去获取的是工厂 实际上获得飞机
        Book book = ioc.getBean("myFactoryBeanImpl", Book.class);
        System.out.println(book);

    }
}
