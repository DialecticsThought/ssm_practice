package liu.test;

import liu.config.MainConfigLifetCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Life {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigLifetCycle.class);

        System.out.println("容器创建完成。。。。");

        applicationContext.close();
    }
}
