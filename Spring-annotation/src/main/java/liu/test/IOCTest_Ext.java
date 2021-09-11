package liu.test;

import liu.ext.Extconfig;
import liu.tx.Txconfig;
import liu.tx.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Ext {

    //1.使用命令行参数的方法 -Dspring.profiles.active=test
    //利用代码的方式
    @Test
    public void test01() {
        //1.创建一个容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Extconfig.class);

        applicationContext.publishEvent(new ApplicationEvent(new String("测试一个事件")) {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        });
        applicationContext.close();
    }
}
