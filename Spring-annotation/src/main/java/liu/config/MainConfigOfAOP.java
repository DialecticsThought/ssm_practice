package liu.config;

import liu.aop.LogAspects;
import liu.aop.MathCalculator;
import org.aspectj.lang.annotation.Before;
import org.aspectj.tools.ajc.Main;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/*
* AOP：秩序在运行期间动态的将某一段代码切入到指定方法 指定位置运行的编程方式
*
*  1.导入AOP模块
*  2. 在业务逻辑运行的时候 添加 日志打印（方法运行前 方法运行后）
*  3：定义一个日志切面类 切面里面的方法需要动态感知到业务逻辑运行到哪里
*       切面类 里面有 通知方法
*                       前置通知 @Before logStart 目标方法运行前
*                       后置通知 logEnd  某表方法 运行结束后 不管正不正常运行结束 都会调用
*                       返回通知 logReturn 目标方法 正常地返回后运行
*                       异常通知 logException 目标方法 出现异常后运行
*                       环绕通知 动态代理  手动推进目标方法运行（joint.Procced（））
* 4.给切面类的通知标注何时何地运行 也就是添加注解
*
*   抽取公共的切入点表达式
    本类引用
    其他类引用 写全名
    @Pointcut("execution(* liu.aop.MathCalculator.*(..))")
    public void point(){
    }
 * 5.将切面类和业务逻辑类都加入到容器中
 *
 * 6. 告诉容器哪一个是切面类@Aspect
 * 7.开启基于注解的aop <aop:aspectj-autoproxy></aop:aspectj-autoproxy> <---> @EnableAspectJAutoProxy
 *
 *  在Spring中有很多 Enablexxxxx 都是为了开启某一个功能
 *
 * 查看LogAspects
 *
 * AOP原理 【给容器注册什么组件 这个组件什么时候工作 这个组件的功能是什么？】
 *
 * 1.@EnableAspectJAutoProxy==》@Import(AspectJAutoProxyRegistrar.class)
 *
 * class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar
 *
 * 利用AspectJAutoProxyRegistrar自定义给容器注册bean 也就是beanDefinition
 *
 *  internalAutoProxyCreator =  AnnotationAwareAspectJAutoProxyCreator
 *
 * AspectJAutoProxyRegistrar里面有一个 registerBeanDefinitions方法
 *
 *
 *
 * 该方法里面有一个AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry)方法
 *
 * 它又返回了registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry, null);
 *
 * registerAspectJAnnotationAutoProxyCreatorIfNecessary 调用了registerOrEscalateApcAsRequired方法
 *
 * 在该方法有RootBeanDefinition beanDefinition = new RootBeanDefinition(cls); 得到bean的定义信息
 * 也就是  [org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator];☆☆☆☆☆☆☆☆☆☆☆☆☆☆
 *
 * 最后 return beanDefinition; 之前  执行registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
 *
 * 2.核心就是在容器中注册下面的这个组件
 * AnnotationAwareAspectJAutoProxyCreator☆☆☆☆☆☆☆☆☆☆☆☆☆☆
 *      extends AspectJAwareAdvisorAutoProxyCreator
 *          extends AbstractAdvisorAutoProxyCreator
 *              extends AbstractAutoProxyCreator
 *                  extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *
 * SmartInstantiationAwareBeanPostProcessor这个bean后置处理器==》 目的是bean初始化前后 要做什么工作
 *
 * BeanFactoryAware中 setBeanFactory(BeanFactory beanFactory)  也就是传了一个bean工厂
 *
 * 在下面的方法打上断点
 * AbstractAutoProxyCreator.setBeanFactory
 *
 * AbstractAutoProxyCreator.postProcessBeforeInstantiation
 *
 * AbstractAutoProxyCreator.postProcessAfterInitialization
 *
 * AbstractAdvisorAutoProxyCreator.setBeanFactory 和initBeanFactory
 *
 * AnnotationAwareAspectJAutoProxyCreator.initBeanFactory 说明这个方法被重写 并且 真正调用的是这个方法 而不是父类方法
 *
 * 还要在这个配置类的方法中打上断点
 *
 * 一开始（看方法栈）
 *  1.AnnotationConfigApplicationContext(MainConfigOfAOP.class); 传入配置类 创建容器
 *  2.注册配置类 调用refresh 刷新容器
 *  3.refresh有一个调用的是AbstractApplicationContext.registerBeanPostProcessors 目的是注册bean的后置处理器Register bean processors that intercept bean creation.
 *      而这个方法调用PostProcessorRegistrationDelegate.registerBeanPostProcessors
 *      该方法有一个String[] postProcessorNames = beanFactory.getBeanNamesForType
 *      1.现货区ioc容器中已经定义了需要创建对象的所有beanPostProcessor
 *      postProcessorNames数组里面有一个  org.springframework.aop.config.internalAutoProxyCreator
 *      2.给容器添加其他的beanPostProcessor
 *      3.优先注册实现了interface PriorityOrdered的beanPostProcessor （看PostProcessorRegistrationDelegate）
 *      4.再注册实现interface Ordered的的beanPostProcessor
 *      5.最后再注册其他的beanPostProcessor
 *      PostProcessorRegistrationDelegate调用BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
 *      而该方法调用了AbstractBeanFactory.doGetBean，该方法调用了AbstractBeanFactory.doGetBean
 *      doGetBean调用了getSingleton 因为一开始没有单例对象  所以调用getObject 而这个方法return createBean(beanName, mbd, args);
 *      6.也就是注册BeanPostProcessor ==》实际上创建BeanPostProcessor对象 并放入容器
 *          也就是创internalAutoProxyCreator的BeanPostProcessor
 *          1.创建bean实例 这bean就是internalAutoProxyCreator 执行AbstractAutowireCapableBeanFactory.createBeanInstance
 *          2.AbstractAutowireCapableBeanFactory.populateBean(beanName, mbd, instanceWrapper);给bean的各种属性赋值
 *          3.AbstractAutowireCapableBeanFactory.initializeBean初始化bean
 *              1.invokeAwareMethods(beanName, bean); 查看是否继承了Aware接口 是的话执行相应的方法
 *              2,调用AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization
 *                这个方法会遍历所有的后置处理器来执行初始化前的工作 并且返回包装类
 *              3.AbstractAutowireCapableBeanFactory.invokeInitMethods执行初始化方法
 *              4.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization 执行 初始化之后的工作
 *              5.BeanFactoryAware.setBeanFactory方法会调用AbstractAdvisorAutoProxyCreator.setBeanFactory
 *              6.AbstractAdvisorAutoProxyCreator.setBeanFactory回调用其父类的setBeanFactory方法
 *              7.AbstractAdvisorAutoProxyCreator回调用其父类的setBeanFactory方法后会调用AbstractAdvisorAutoProxyCreator.initBeanFactory
 *          4.BeanPostProcessor(AbstractAdvisorAutoProxyCreator)创建成功
 *
 *       7.把BeanPostProcessor注册到BeanFactory中
 *          beanFactory.addBeanPostProcessor(postProcessor)
 *
 * ======以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程==========
 *
 *
 *
 *     AnnotationAwareAspectJAutoProxyCreator的后置处理器有点特殊是 postProcessBeforeInstantiation
 *  4.AbstractApplicationContext.finishBeanFactoryInitialization完成 BeanFactory初始化工作 创建剩下的单实例bean ==》preInstantiateSingletons()
 *          1.遍历获取容器所有的bean 依次创建对象 在preInstantiateSingletons()中
 *                      List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
 *                      for (String beanName : beanNames)
 *
 *           根据方法栈getBean--》doGetBean--》getSingleton--》getObject☆☆☆☆☆☆☆☆
 *          【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截 因为它的后置处理器是instantiationAwareBeanPostProcessor】
 *
 *          2.创建bean
 *              1.doGetBean中 根据Object sharedInstance = getSingleton(beanName);
 *              先从缓存中获取当前的bean如果能获取到 说明bean是之前被创建过的话 直接使用
 *              只要创建好的bean会被 缓存起来  保证单例的时候会用到
 *              2.创建bean  调用 createBean方法 ====》AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截 因为后置处理器是instantiationAwareBeanPostProcessor
 *                      【BeanPostProcessor是bean对象创建完成初始化前后调用的】
 *                      【instantiationAwareBeanPostProcessor是在创建bean实例之前 先尝试使用后置处理返回对象】
 *                      1.AbstractAutowireCapableBeanFactory.createBean方法
 *                      中有句话 Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
 *                      希望后置处理器返回一个代理对象 能的话 就使用代理对象 不能的话 继续向下执行resolveBeforeInstantiation方法：
 *                            1.后置处理器先尝试返回一个对象 利用方法AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInstantiation
 *                            而这个方法是：  拿到所有后置处理器 如果是instantiationAwareBeanPostProcessor类型
 *                            就会执行InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation方法
 *                            2：applyBeanPostProcessorsBeforeInstantiation执行完以后 向下执行
 *                              if (bean != null) {
						            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
					            }
 *                      2.调用doCreateBean去创建一个bean实例 和3.6流程一模一样
 *
 * AnnotationAwareAspectJAutoProxyCreator的instantiationAwareBeanPostProcessor作用
 *      1.在每一个bean创建之前 调用方法PostProcessorsBeforeInstantiation
 *          关心MainCalculator和LogAspect的创建
 *          1.containsKey判断当前的bean是否在advisedBeans中（ 保存了所有需要增强的bean）
 *          2.isInfrastructureClass判断当前的bean是否是基础类型 或者是切面 有好几个判断 如下面所示
 *              boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
				Pointcut.class.isAssignableFrom(beanClass) ||
				Advisor.class.isAssignableFrom(beanClass) ||
				AopInfrastructureBean.class.isAssignableFrom(beanClass);
		    3.是否需要跳过
                    1.（AspectJAwareAdvisorAutoProxyCreator.shouldSkip）
 *                  获取候选的增强器（切面里面的通知方法）List<Advisor> candidateAdvisors = findCandidateAdvisors();
 *                  每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor
 *                  判断每一个增强器是否是AspectJPointcutAdvisor类型的 是的话 就返回true （查看shouldSkip方法）
 *                  2.永远返回false
 *      2.创建对象
 *      调用AbstractAutoProxyCreator.postProcessAfterInitialization的方法
 *          方法里的最后return wrapIfNecessary(bean, beanName, cacheKey); 是否需要增强
 *          1.而这个返回方法里面有一个方法Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
 *            用来获取当前的bean的所有增强器（通知方法）
 *              1.找到候选的所有的增强器（哪些通知方法需要切入到bean方法）
 *              2.获取到能在bean使用的增强器
 *              3.给增强器排序
 *          2.保存当前bean在advisedBeans里面 this.advisedBeans.put(cacheKey, Boolean.TRUE);
 *          3.如果当前bean需要需要增强 创建代理对象
 *              Object proxy = createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
                    1.获取所有增强器（通知方法） Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
                    2.保存到proxyFactory  proxyFactory.addAdvisors(advisors);
                    3.createProxy方法最后执行的是return proxyFactory.getProxy(classLoader);利用代理工厂创建代理对象
*                       创建代理对象 Spring自动决定
*                           JDKDynamicAopProxy(config) jdk动态代理
*                           ObjenesisiCglibAopProxy(config) cglib动态代理
*            4.给容器中返回当前组件使用的cglib增强的代理对象
*            5.以后容器拿到的是该组件的代理对象 执行目标方法的时候 代理对象会执行通知方法的流程
*
*       断点打在div（）方法上
*
*       3.目标方法的执行（代理对象）
*           容器中保存了组件的代理对象（cglib增强后的对象） 这个对象里面保存了详细信息（增强器，目标对象）
*           1.CglibAopProxy.DynamicAdvisedInterceptor.intercept 拦截方法
*           2.根据ProxyFactory对象获取将要执行的目标方法的拦截器链 List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
*              1.执行方法进入会执行 List<Object> interceptorList = new ArrayList<>(advisors.length);
*              创建一个list 长度是增强器的个数
*              2.遍历所有增强器 都会转成interceptors
*               并执行MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
*              3.getInterceptors方法里面有会执行
*                       List<MethodInterceptor> interceptors = new ArrayList<>(3);
*                       Advice advice = advisor.getAdvice();
*                 判断 advice instanceof MethodInterceptor
*                 是的话 加入到interceptors 集合
*                 不是的话 通过一系列的适配 比如用到AdvisorAdapter 强转成MethodInterceptor类型
*                          并加入到interceptors 集合
*
*           3.如果没有拦截器链 直接执行目标方法 retVal = methodProxy.invoke(target, argsToUse);
*               拦截器链《===》每一个通知方法被包装成拦截器 利用MethodInterceptor
*           4.如果有拦截器链 把需要执行的目标对象 目标方法 传入一个对象 并执行proceed方法
*               retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
*
*           5.执行目标方法org.springframework.aop.framework.ReflectiveMethodInvocation.proceed
*
*               1.说明如果没有拦截器 /执行到最后一个拦截器 ===> 执行目标方法
*               currentInterceptorIndex用来记录拦截器的索引
*               if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
			        return invokeJoinpoint();
		        }
*               2.脸是获取每一个拦截器 烂机器执行invoke方法 每一个烂机器等待下一个拦截器执行完成 并 返回 以后 在继续执行
*               拦截器链的机制 保证通知方法与目标方法的执行顺序
*
* 总结
*@EnableAspectJAutoProxy会注册一个组件AnnotationAwareAspectJAutoProxyCreator
*AnnotationAwareAspectJAutoProxyCreator是一个后置处理器
* 容器的创建流程
*       1.refresh中有一个registerBeanPostProcessors（）注册后置处理器AnnotationAwareAspectJAutoProxyCreator
*       2.refresh中还有一个finishBeanFactoryInitialization（）；方法初始化剩下的单实例bean
*               1.创建业务逻辑组件和切面组件
*               2.annotationAwareAspectJAutoProxyCreator会拦截组件的创建过程
*               3.组建创建完之后 判断组件是否需要增强
*                       是： 切面的通知方法 包装秤增强器advisor 给业务逻辑组件创建一个代理对象
*       3.执行目标方法
*               1.代理对象执行目标方法
*               2.CglibAopProxy.DynamicAdvisedInterceptor.intercept方法
*                       1.得到目标方法的拦截器链（增强器被包装转成的）
*                       2.利用拦截器的链式机制 依次进入每一个拦截器并执行
*                       3.效果
*                               前置通知-》目标方法执行-》后置通知-》返回通知
*                               前置通知-》目标方法执行-》后置通知-》异常通知
*
*
* */
@Configuration
@EnableAspectJAutoProxy
public class MainConfigOfAOP {

    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
