package ljh.test;

import ljh.impl.MyCalculator;
import ljh.inter.Calculator;
import ljh.proxy.CalculatorProxy;
import org.junit.Test;

import java.util.Arrays;

public class AOPTest {
    /*
    * 有了动态代理 日志记录可以做的非常强大 而且与业务逻辑解耦
    *
    * jdk默认的动态代理 如果目标对象没有实现任何接口 无法创建代理对象
    *
    * */
    @Test
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
    }
}
