package liu.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/*
* beanFactoryPostProcessor beanFactory的后置处理器
*       在beanFactory标准初始化之后 所有的bean定义已经保存加载到beanFactory 但是bean实例没有被创建
*
* */
@Component
public class MyBeanFactroyPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor...");
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

        System.out.println("当前bean工厂有："+beanFactory.getBeanDefinitionCount()+"个bean定义");

        for (String name:beanDefinitionNames){
            System.out.println(name);
        }
    }
}
