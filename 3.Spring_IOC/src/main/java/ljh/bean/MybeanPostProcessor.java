package ljh.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/*
* 形参Object bean将要初始化的bean
* String beanName 在xml配置文件中的id
*
* 1.编写后置处理器的实现类
* 2.所有的组件想要工作必须注册在配置文件中☆☆☆☆☆☆
* */
public class MybeanPostProcessor implements BeanPostProcessor {
    //初始化后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(bean+"bean调用初始化方法结束。。");
        //返回的是吃石化之后的bean 返回的时候是什么，容器中保存的就是什么
        return bean;
    }
    //初始化前调用
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(bean+"bean将要调用初始化方法。。");
        //返回传入的bean
        return bean;
    }
}
