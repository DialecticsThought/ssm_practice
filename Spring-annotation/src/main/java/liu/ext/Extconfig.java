package liu.ext;

import liu.bean.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/*
* 扩展原理
* beanPostProcessor bean后置处理器 bean创建对象初始化前后进行拦截工作
* beanFactoryPostProcessor beanFactory的后置处理器
*       在beanFactory标准初始化之后 所有的bean定义已经保存加载到beanFactory 但是bean实例没有被创建
*
* 1.ioc容器创建对象中 有refresh()方法
* 2.调用了refresh()方法的invokeBeanFactoryPostProcessors(beanFactory);
* invokeBeanFactoryPostProcessors(beanFactory)方法会调用PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors
*       如何找到所有的beanFactoryPostProcessor并执行他们的方法:
*               1.PostProcessorRegistrationDelegate类中 先获得所有的beanFactoryPostProcessor名字
*               String[] postProcessorNames =beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
*               再根据priorityOrdered, Ordered, and the rest这三种情况分类
*               最后再一一执行
*               2.在初始化创建其他组件前执行 也就是finishBeanFactoryInitialization(beanFactory)执行前
*               执行	invokeBeanFactoryPostProcessors(beanFactory); 可以查看AbstractApplicationContext类
*
*
*
*
* BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
*           1.所有bean的定义信息将要被加载 bean实例还没有创建的时候  执行postProcessBeanDefinitionRegistry
*           2.优于beanFactoryPostProcessor执行
*           3.利用 BeanDefinitionRegistryPostProcessor还可以额外添加一些组件
*
* 原理
*   1.ioc创建对象
*   2.refresh->invokeBeanFactoryPostProcessor
*   3.从容器中得到所有的BeanDefinitionRegistryPostProcessor组件
*           1、依次执行postProcessBeanDefinitionRegistry方法 （具体可以查看invokeBeanDefinitionRegistryPostProcessor）
*           2.再来触发BeanDefinitionRegistryPostProcessor的postProcessBeanFactory方法 具体可查看invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
*   4.再从容器中找到beanFactoryPostProcessor组件 再执行beanFactoryPostProcessor的postProcessBeanFactory方法
*
*
*   ApplicationListener 监听容器中发布的时间 事件驱动模型开发
*   监听 ApplicationEvent及其子事件  也就是子类  泛型就是那个事件
*   interface ApplicationListener<E extends ApplicationEvent> extends EventListener
*
*   步骤
*       写一个监听器监听某个事件（ApplicationEvent及其子事件）
*       把监听器放入容器中
*       容器有相应的事件发布 就能监听到
*           eg:ContextRefreshedEvent 容器刷新完成(所有bean创建完成） 会发布这个事件
*              ContextClosedEvent   容器关闭 会发布这个事件
*       发布一个事件
*           applicationContext.publishEvent
*    原理
*    1).ContextRefreshedEvent事件
*       1.容器创建对象 refresh
*       2.finishRefresh()容器刷新完成  发布ContextRefreshedEvent事件
*    2）.自己发布事件
*    3）.容器关闭事件
*    事件发布的流程
*       3.publishEvent(new ContextRefreshEvent)在 finishRefresh()方法里面 对应了上面的
*              1 获得事件的多播器 发送给多个监听器	getApplicationEventMulticaster()【在publishEvent方法里面】
*              2 再调用multicastEvent(applicationEvent, eventType); 派发事件
*                      1得到所有的ApplicationListener 通过for循环
*                      2如果有Executor 可以异步派发
*                      3.没有Executor 就同步直接执行listener方法 执行方法invokeListener
*                      4.拿到listener 回调OnApplicationEvent方法
*
*   事件多播器（派发器）
*       1.容器创建对象 会调用refresh()
*       2.refresh()方法中有一个initApplicationEventMulticaster();
*           1.先去容器中找有没有applicationEventMulticaster这个组件 beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
*           2.没有的话 容器自己创建  this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
*              注册完自己加入注册   beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
*               程序员可以在其他组件中想要派发事件的时候 自动注入applicationEventMulticaster
*
*   getApplicationEventMulticaster()怎么知道容器有哪些监听器
*           refresh()有一个registerListeners();方法
*           1.该方法 获取到所有监听器
*           for (ApplicationListener<?> listener : getApplicationListeners()) {
			    getApplicationEventMulticaster().addApplicationListener(listener);
		    }
*           2.把所获得的监听器 注册到applicationEventMulticaster
		    String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
		    for (String listenerBeanName : listenerBeanNames) {
			getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
		    }
 * */
@ComponentScan("liu.ext")
@Configuration
public class Extconfig {

    @Bean
    public Blue blue(){
        return new Blue();
    }
}
