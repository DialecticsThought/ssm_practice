package liu.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//判断是否是win系统
public class WinCondition implements Condition {
    /*
     * onditionContext context判断条件能使用的上下文环境
     * AnnotatedTypeMetadata metadata 注释ed信息
     * */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //判断是否是win系统
        //的到ioc使用的beanfactory 用来装配和使用
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //获得类加载器
        ClassLoader classLoader = context.getClassLoader();
        //获得环境信息
        Environment environment = context.getEnvironment();
        //获得bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();

        String system = environment.getProperty("os.name");

        if(system.contains("Win")){
            return true;
        }
        return false;
    }
}
