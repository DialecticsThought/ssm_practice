package liu.config;

import liu.bean.Car;
import liu.bean.Color;
import liu.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/*
* 自动装配
*   Spring 利用依赖注入（DI） 完成对IOC容器中各个组件的依赖关系赋值
* 依赖： 某个类和另一个类有关系 具体什么关系（eg：泛化等）可以先不用管
*   1.@Autowired:自动注入
*   BookService{
*           1.默认优先 按照类型去容器中找对象的组件
*                   applicationContext.getBean(BookDao.class);
*           2.如果找到了多个相同类型的组件 再将属性的名称 作为组件的id 去容器中查
*                   applicationContext.getBean("BookDao2");
*           3.自动装配 默认是 一定要找到 如果没有注册这个组件 也就是bookDao 就会报错
*               除非(required = false) 可以查看BookService.class
*           4.@Qualifier("bookDao") 指定 需要装配的组件的id 而不是根据属性
*           5.@Primary表示首选使用哪一个组件装配
*                       也可以继续使用@Qualifier指定需要装配的bean的名字
*           @Autowired(required = false)
*           BookDao bookDao2
*       }
*   2.Spring还支持使用@Resource @Inject 这两个是java的标准
*   @Resource不支持  @Primary和 @Autowired(required = false)
*   @Inject需要导入javax.Inject 并且 没有required = false功能
*
*   AutowiredAnnotationBeanPostProcessor 解析这些注解 完成自动装配
*
*   eg:boss
*   3.@Autowired 放在构造器方法上 （如果该组件只有一个有参构造器 这个有参构造器的@autowired可以省略） 使用的参数组件的值 是从 ioc容器中获取
*              放在形参上       使用的参数 组件的值  是从 ioc容器中获取
*              放在setting方法   使用的参数 组件的值  是从 ioc容器中获取
*
*               @Bean+方法参数 参数是从容器中拿去 默认不写 @Autowired是一样的都能自动装配
*               在Configuration里面
*                   @Bean
                    public Color color(Car car){
                    Color color = new Color();
                    color.setCar(car);
                    return color;
                    }
                    *
*   4.自定义组件想要使用Spring容器底层的一些组件(ApplicationContext,beanFactory.....)
*   自定义xxxxAware ：在创建对象的时候 会调用接口规定的方法注入相关组件
*   可以看Red.class    把底层一些组件注入到bean里面
*   ApplicationContextAware是由 ApplicationContextAwareProcessor处理的 也就是后置处理器
*   总结：xxxxAware  ==> xxxAwareProcessor处理
*   建议在Red类里面添加断点
*       查看  initializeBean==》applyBeanPostProcessorsBeforeInitialization==》postProcessBeforeInitialization
 * */
@ComponentScan(value = "liu")
@Configuration
public class MainConfigOfAutowired {
    @Bean("bookDao01")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();

        bookDao.setLable("2");
        return  bookDao;
    }
    /*
    * Color类里面有一个car属性的值
    * @Bean标注的方法创建对象的时候 方法参数的值是从容器中拿
    *
    * */
    @Bean
    public Color color(Car car){
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}
