package liu.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener {
    //当容器中发布此事件 方法会触发
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到事件"+event);
    }
}
