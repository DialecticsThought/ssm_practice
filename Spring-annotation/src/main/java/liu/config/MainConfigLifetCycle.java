package liu.config;

import liu.bean.Car;
import liu.bean.Cat;
import liu.bean.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
* bean的生命周期
*   bean创建---->初始化--->销毁的过程
* 容器管理bean的生命周期
*   可以自定义初始化和销毁的方法
*1.bean.xml中需要 init-method  destroy-method 指定
*构造（对象创建）
*   单实例：容器启动的时候创建对象
*           AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
*   多实例：在每次获取的时候创建对象
*           Person person = (Person) annotationConfigApplicationContext.getBean("person")
*2.@Bean
*   init 对象创建完成 并复制好 调用初始化方法
*          区分多实例 单实例  在bean容器中的创建时机
*
*   destroy  单实例在 applicationContext.close();之后执行
*           多实例 容器不会调用
*
* 3.通过让bean 实现 InitializingBean 定义初始化逻辑, DisposableBean定义销毁逻辑
*
* 4.使用JSR250
*   @PostConstruct在bean创建完成并且属性赋值完成后 执行初始化
*   @PreDestory在容器销毁bean之前 提醒程序员清理工作
* 5.利用bean的后置处理器
*       BeanPostProcessor{interface} 做一些初始化前后的处理工作
*       postProcessBeforeInitialization 初始化之前
*       postProcessAfterInitialization  初始化之后
*
* applyBeanPostProcessorsBeforeInitialization ==》 遍历BeanPostProcessor ==》挨个执行postProcessBeforeInitialization
* 但是一旦返回 null 就会跳出for循环
*
*  public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException {

		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}
*
* 执行顺序
* postProcessBeforeInitialization==》initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition mbd)
* ==》 postProcessAfterInitialization
*
* 但是这三个方法的执行都在populateBean之后 这个方法是用来给bean属性赋值
*
*
* 总之 Spring底层对beanPostProcessor的使用 bean赋值 注入其他组件 @autowired 生命周期注解功能等
*
*
* 查看Dog类
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
@ComponentScan("liu")
@Configuration
public class MainConfigLifetCycle {
   // @Bean(value = "car01",initMethod = "init" ,destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }

    @Bean("cat")
    public Cat cat(){
        return new Cat();
    }
    @Bean("dog")
    public Dog dog(){
        return new Dog();
    }
}
