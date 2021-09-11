package liu.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Dog implements ApplicationContextAware {
    //定义一个变量用来保存容器
    ApplicationContext applicationContext;

    public Dog() {
        System.out.println("dog...constructor...");
    }
    //在bean创建完成后并且属性赋值完成后
    //@PostConstruct
    public void init(){
        System.out.println("dog...init..");
    }
    //在容器销毁eban之前 提醒清理工作
    //@PreDestory
    public void destroy(){
        System.out.println("dog...destroy...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
    /*
    * ApplicationContextAwareProcessor的postProcessBeforeInitialization方法
    * 会判断
    * if (!(bean instanceof EnvironmentAware || bean instanceof EmbeddedValueResolverAware ||
				bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware ||
				bean instanceof MessageSourceAware || bean instanceof ApplicationContextAware ||
				bean instanceof ApplicationStartupAware)) {
			return bean;
		}
    * 如果继承了 ApplicationContextAware
    * 执行invokeAwareInterfaces(bean); 注入值
    *
    * 		if (bean instanceof ApplicationContextAware) {
			((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
		}
    * */
}
