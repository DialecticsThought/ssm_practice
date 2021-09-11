package liu.config;
/*
*
* spring可以根据当前的环境 动态的激活和切换一系列组件的功能
*
* 开发环境 测试环境  生产环境
*
* 数据源： A  B  C
*
* @profile指定组件在哪一个环境的情况下才能被注册到容器中 不用的话 不指定 任何环境下都能注册该组件
*
* 方法上加了环境@profile表示的bean只有这个环境被激活 组件才会被注册到容器中
*
* @profile写在配置类上  只有这个环境被激活 配置才会被生效
*
* 没有标注环境标识的bean在任何环境下都加载
*
* 默认是default环境
*
*1.使用命令行参数的方法 -Dspring.profiles.active=test
*
*2.利用代码的方式
* 1.创建一个容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
  2.设置需要激活的环境
    applicationContext.getEnvironment().setActiveProfiles("test","dev");
  3.注册配置类
     applicationContext.register(MainConfigOfProfile.class);
  4.启动刷新容器
     applicationContext.refresh();
* */

import com.mchange.v2.c3p0.ComboPooledDataSource;
import liu.bean.Blue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

//写在配置类上  只有这个环境被激活 配置才会被生效
//@Profile("test")
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware {
    //可以查看之前的@Value
    @Value("${db.user}")
    private String user;
    //定一个这个变量 用来保存字符串解析器
    StringValueResolver resolver;
    //通过字符串解析器 解析到环境变量中的 db.dirverClass
    private String driverClass;
    //用来测试
    @Bean
    //@Profile("test")
    public Blue blue(){
        return new Blue();
    };

    @Profile("test")
    @Bean("devDataSource")
    public DataSource dataSourceTest(@Value("${db.password}")String password) throws PropertyVetoException  {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        comboPooledDataSource.setUser(user);

        comboPooledDataSource.setPassword(password);

        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");

        comboPooledDataSource.setDriverClass(driverClass);

        return comboPooledDataSource;
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}")String password) throws PropertyVetoException  {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        comboPooledDataSource.setUser(user);

        comboPooledDataSource.setPassword(password);

        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");

        comboPooledDataSource.setDriverClass(driverClass);

        return comboPooledDataSource;
    }

    @Profile("prod")
    @Bean("proDataSource")
    public DataSource dataSourceProd(@Value("${db.password}" )String password )throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        comboPooledDataSource.setUser(user);

        comboPooledDataSource.setPassword(password);

        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis_plus");

        comboPooledDataSource.setDriverClass(driverClass);

        return comboPooledDataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver=resolver;

        driverClass = resolver.resolveStringValue("${db.dirverClass}");
    }
}
