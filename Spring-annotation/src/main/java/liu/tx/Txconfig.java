package liu.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/*
*
* 事务
*
* 环境搭建
*   1.导入相关依赖
*       数据源 数据库驱动 spring-jdbc模块
*   2.配置数据源 JdbcTemplate(spring提供的简化数据库操作的工具)操作数据
*   3.给方法上标注@Transactional表示当前方法是一个事务方法
*   4.@EnableTransactionManagement//开启基于注解的书屋管理功能<tx:annotation-driven/>
*       @Enablexxx表示开启某一个功能
*   5.开启事务管理器☆☆☆☆☆☆☆☆☆
*   @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
  *
  * @EnableTransactionManagement利用@Import(TransactionManagementConfigurationSelector.class)导入了一些组件
  *
  * 1.@EnableTransactionManagement会定一个属性AdviceMode.PROXY.
  *         TransactionManagementConfigurationSelector会根据这个属性 会导入AutoProxyRegistrar 和ProxyTransactionManagementConfiguration
  *
  * AutoProxyRegistrar implements ImportBeanDefinitionRegistrar 所以会给容器中注册bean
  *
  * AutoProxyRegistrar 中的方法registerBeanDefinitions调用registerAutoProxyCreatorIfNecessary
  *
  * registerAutoProxyCreatorIfNecessary调用registerAutoProxyCreatorIfNecessary
  *
  * 2.registerAutoProxyCreatorIfNecessary 这个方法会注册一个InfrastructureAdvisorAutoProxyCreator组件
  *
  * InfrastructureAdvisorAutoProxyCreator利用后置处理器机制 在对象互藏建议后 包装对象 并返回一个代理对象（代理对象包含增强器）
  * 代理对象 执行方法 利用拦截器链进行调用
  *
  * 3.ProxyTransactionManagementConfiguration利用transactionAdvisor方法
  *             1.给容器中添加了一个事务增强器
  *                     事务增强其要用事注解的信息 AnnotationtransactionAttributeSource 解析事务的注解
  *             2.事务拦截器transactionInterceptor
  *                     保存了事务的属性信息 事务管理器
   *                    transactionInterceptor implements MethodInterceptor 也就类似aop
   *                    目标方法是由代理对象执行 会执行拦截器链：
   *                                    1.获取事务相关的属性 （可以查看transactionInterceptor中的invoke方法中的invokeWithinTransaction
  *                                     2.获取TransactionManager (方法invokeWithinTransaction有一个通过determineTransactionManager得到
  *                                         如果没有事先添加transactionManager（比如说通过注解的方法） 那么最终在容器中获取transactionManager
  *                                     3.执行目标方法
  *                                         如果异常 获取事务管理器 利用事务管理器回滚 查看completeTransactionAfterThrowing(txInfo, ex);
  *                                         如果正常 利用事务管理器 提交事务 查看commitTransactionAfterReturning(txInfo);
  *
  * invokeWithinTransaction中有一段代码
  * if (txAttr == null || !(ptm instanceof CallbackPreferringPlatformTransactionManager)) {
			// Standard transaction demarcation with getTransaction and commit/rollback calls.
			TransactionInfo txInfo = createTransactionIfNecessary(ptm, txAttr, joinpointIdentification);

			Object retVal;
			try {
				// This is an around advice: Invoke the next interceptor in the chain.
				// This will normally result in a target object being invoked.
				retVal = invocation.proceedWithInvocation();
			}
			catch (Throwable ex) {
				// target invocation exception
				completeTransactionAfterThrowing(txInfo, ex);
				throw ex;
			}
			finally {
				cleanupTransactionInfo(txInfo);
			}
  * if (retVal != null && vavrPresent && VavrDelegate.isVavrTry(retVal)) {
	    // Set rollback-only in case of Vavr failure matching our rollback rules...
		TransactionStatus status = txInfo.getTransactionStatus();
		if (status != null && txAttr != null) {
					retVal = VavrDelegate.evaluateTryFailure(retVal, txAttr, status);
		}
	}

	commitTransactionAfterReturning(txInfo);
    return retVal;
* */
@EnableTransactionManagement//开启基于注解的书屋管理功能<tx:annotation-driven/>
@ComponentScan("liu.tx")
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class Txconfig implements EmbeddedValueResolverAware {
    //可以查看之前的@Value
    @Value("${db.user}")
    private String user;
    //定一个这个变量 用来保存字符串解析器
    StringValueResolver resolver;
    //通过字符串解析器 解析到环境变量中的 db.driverClass
    private String driverClass;

    @Bean("dataSource")
    public DataSource dataSource(@Value("${db.password}")String password) throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        comboPooledDataSource.setUser(user);

        comboPooledDataSource.setPassword(password);

        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/tx");

        comboPooledDataSource.setDriverClass(driverClass);

        return comboPooledDataSource;
    }
    //自动地把容器中的dataSource组件放入形参
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate;
    }
    //在容器中注册事务管理器
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver=resolver;

        driverClass = resolver.resolveStringValue("${db.driverClass}");
    }
}
