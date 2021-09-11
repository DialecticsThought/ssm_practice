package liu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    private ApplicationContext applicationContext;

    //容器创建对象的时候 会返回一个容器
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
        System.out.println("容器是："+applicationContext);
    }
    //容器创建对象的时候 会返回当前bean的名字
    @Override
    public void setBeanName(String name) {
        System.out.println("当前bean的名字是："+name);
    }
    //容器创建对象的时候 会传入一个StringValueResolver 用来解析字符串特殊的符号的
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String s = resolver.resolveStringValue("你好${os.name} 我是#{24-2}");

        System.out.println(s);
    }
}
