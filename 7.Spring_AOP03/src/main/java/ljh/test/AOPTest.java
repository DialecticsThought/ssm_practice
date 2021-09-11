package ljh.test;

import ljh.impl.MyCalculator;
import ljh.inter.Calculator;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest {
    /*
    * 有了动态代理 日志记录可以做的非常强大 而且与业务逻辑解耦
    *
    * jdk默认的动态代理 如果目标对象没有实现任何接口 无法创建代理对象
    *
    * */
    /*  细节3：
        try{
            @Before
            method.invoke()
            @AfterReturning
        }catch(Exception e){
            @AfterThrowing
        }finally{
            @After
        }

    * 通知方法的执行顺序
    *   正常执行
    *       @Before  -->@AfterReturning --> @After
    *   异常执行
            @Before  -->@AfterThrowing --> @After
    * */
/*    @Test
     public void test(){
        MyCalculator myCalculator = new MyCalculator();
        myCalculator.add(1,2);

        System.out.println("============");
        //用代理对象执行加减乘除 代理对象最后也是调用真正的加减乘除 但是可以前后干其他事
        Calculator proxy = CalculatorProxy.getProxy(myCalculator);
        //代理对象真正的类型 不是Caculator 也不是MyCaculator类型 能实现加减乘除的所有功能是因为传入了所有接口
        //代理对象和被代理对象唯一能产生的关联就是实现了同一接口
        System.out.println(proxy.getClass());
        System.out.println(Arrays.asList(proxy.getClass().getInterfaces()));
        proxy.add(2,1);
    }*/
    @Test
    public void test02(){
        //从ico容器中拿到目标对象 注意 如果想要用类型 一定要用他的接口类型 不要用它本类
        ClassPathXmlApplicationContext aop = new ClassPathXmlApplicationContext("aop.xml");
        Calculator bean = aop.getBean(Calculator.class);
        bean.add(2,1);
    }

    /*
     * 细节1： bean.getClass() = class com.sun.proxy.$Proxy19
     * aop底层就是动态代理 容器中保存的组件就是它的代理对象 不是本类的类型
     * 代理对象和本类对象唯一有联系的地方就是接口
     * */
    @Test
    public void test03(){
        //从ico容器中拿到目标对象 注意 如果想要用类型 一定要用他的接口类型 不要用它本类
        ClassPathXmlApplicationContext aop = new ClassPathXmlApplicationContext("aop.xml");
        Calculator bean = aop.getBean(Calculator.class);
        bean.add(2,1);
        System.out.println(bean);
        System.out.println(bean.getClass());
        //不能强转成MyCalculator 只能强转成Calculator
        Object myCaculator = aop.getBean("myCalculator");
    }
    /*
    * 没有接口就是本类类型
    * class ljh.impl.MyCalculator$$EnhancerBySpringCGLIB$$5cb15a6
    * cglib帮我们创建好代理对象
    * */
    @Test
    public void test04(){
        ClassPathXmlApplicationContext aop = new ClassPathXmlApplicationContext("aop.xml");
        //没有接口就是本类类型
        MyCalculator bean = aop.getBean(MyCalculator.class);
        bean.add(2,1);

        //bean.div(1,0);
        System.out.println(bean);
        System.out.println(bean.getClass());
    }


}
