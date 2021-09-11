package liu.test;

import liu.aop.MathCalculator;
import liu.bean.Blue;
import liu.config.MainConfigOfAOP;
import liu.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class IOCTest_aop {

    //1.使用命令行参数的方法 -Dspring.profiles.active=test
    //利用代码的方式
    @Test
    public void test01() {
        //1.创建一个容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        //不要自己创建对象 要用容器中的
        MathCalculator bean = applicationContext.getBean(MathCalculator.class);

        int div = bean.div(1, 0);

        applicationContext.close();
    }
}
