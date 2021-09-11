package liu.config;


import ch.qos.logback.core.util.COWArrayList;
import liu.bean.Color;
import liu.bean.ColorFactory;
import liu.bean.Person;
import liu.condition.LinuxCondition;
import liu.condition.MyImportBeanDefinitionRegistrar;
import liu.condition.MyImportSelector;
import liu.condition.WinCondition;
import org.springframework.context.annotation.*;

//满足当前条件 这个类中配置的所有bean注册才会生效
//@Conditional({WinCondition.class,LinuxCondition.class})
//@Configuration
@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})//快速导入组件 id默认是组建的全类名
public class MainConfig2 {
    /*
    * 作用域范围
    * ConfigurableBeanFactory.SCOPE_PROTOTYPE, 多实例 ioc容器启动不会去调用方法创建对象放在容器中 每次获取的时候才会调用方法创建对象
    * ConfigurableBeanFactory.SCOPE_SINGLETON, 单实例 ioc容器启动调用方法创建对象放到ioc容器中 以后每次获取都是从容器中拿（类似于map.get()）
    * org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST, 同一个请求单实例
    * org.springframework.web.context.WebApplicationContext.SCOPE_SESSION, 同一个session单实例
    *
    * 懒加载 针对 单实例bean 默认在容器启动的时候创建对象
    *                       AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
    *                       容器启动的时候不创建对象 第一次使用bean的时候创建对象 并初始化
    *                       Person person = (Person) annotationConfigApplicationContext.getBean("person")
    * 看MainConfigLifeCycle
    *构造（对象创建）
     *   单实例：容器启动的时候创建对象
     *           AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
     *   多实例：在每次获取的时候创建对象
     *           Person person = (Person) annotationConfigApplicationContext.getBean("person")
    * */
    @Lazy
    @Scope("prototype")
    //默认是单实例
    @Bean("person")
    public Person person(){
        System.out.println("给容器添加person对象。。。");
        return new Person("ac",2);
    }

    /*
    *
    *  查看 condition包下面的类☆☆☆☆☆☆☆☆☆☆☆☆☆
    *
    * @Conditional 按照一定的条件进行判断 满足@Conditional注解里面的类里面的条件给容器中注册bean
    *  实现方法： 实现某一个接口
    *   实现
    * 如果系统是windows 给容器中注册 bill
    * 如果是linux 给容器中注册linus
    *
    *  @Conditional({WinCondition.class})表示满足WinCondition.class里面的条件 就会创建bean对象
    *
    * */
    @Conditional({WinCondition.class})
    @Bean("bill")
    public  Person person01(){
        return new Person("bill",70);
    }
    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person02(){
        return new Person("linus",48);
    }

    /*
    * 注册组件方法
    * 1.包扫描+注解（@Controller/@Service/@Repository/@Component）==>针对自己写的
    * 2.@Bean==>导入第三方包里面的组件
    * 3.@Import==>快速给容器中导入一个组件 id默认是组建的全类名
    *   @ImportSelector:返回需要导入的组件的全类名的数组  根据全类名把组件导入到容器中
    *   @ImportBeanDefinitionRegistrar
    * 4.使用spring的factoryBean创建
    *       返回的是color对象 而不是工厂类
    *       Object colorFactory1 = annotationConfigApplicationContext.getBean("colorFactory");
    *       返回的是是工厂类对象  而不是color对象
    *       Object colorFactory1 = annotationConfigApplicationContext.getBean("&colorFactory");
    * */
    @Bean
    public ColorFactory colorFactory(){
        return new ColorFactory();
    }
}
