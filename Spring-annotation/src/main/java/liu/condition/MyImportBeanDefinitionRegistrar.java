package liu.condition;

import liu.bean.Rainbow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /*
    * BeanDefinitionRegistry registry  BeanDefinition注册类 把所有需要添加到的容器的bean
    * AnnotationMetadata importingClassMetadata 当前类注解信息
    *
    * BeanDefinitionRegistry.registerBeanDefinition手工注册进来
    * */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        boolean red = registry.containsBeanDefinition("Red");
        boolean blue = registry.containsBeanDefinition("Blue");
        if(red&&blue){
            //指定一个bean的定义信息 也就是bean的类型
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Rainbow.class);
            //指定这个bean的名字 而可以不是全类名
            registry.registerBeanDefinition("rainbow",rootBeanDefinition);
        }
    }
}
