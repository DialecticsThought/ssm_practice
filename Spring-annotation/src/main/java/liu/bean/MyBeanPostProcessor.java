package liu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/*
* 5.利用bean的后置处理器
*    BeanPostProcessor 做一些初始化前后的处理工作
*       postProcessBeforeInitialization 初始化之前
*       postProcessAfterInitialization  初始化之后
*
* */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    /*
    * 容器创建的实例
    * 容器创建的实例的名字
    *
    * */
    /*
    * The default implementation returns the given bean as-is.
    Params:
    bean – the new bean instance
    beanName – the name of the bean
    Returns:
    the bean instance to use, either the original or a wrapped one; if null, no subsequent BeanPostProcessors will be invoked
    *
    * */
    //初始化之前
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization..."+bean+" ==> "+beanName);
        return bean;
    }
    //初始化之后
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization..."+bean+"  ==>  "+beanName);
        return bean;
    }
}
