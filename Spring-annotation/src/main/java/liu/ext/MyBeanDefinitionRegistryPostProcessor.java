package liu.ext;

import liu.bean.Blue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    //registry就是bean定义信息的存储中心
    // 以后beanFactory就是按照BeanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry"+registry.getBeanDefinitionCount());

        RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
        //第一个参数是bean的名字 第二个是bean   来达到创建一个bean对象到组件中
        registry.registerBeanDefinition("hello",beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanDefinitionRegistryPostProcessor....bean的数量："+beanFactory.getBeanDefinitionCount());
    }
}
