AOP aspect oriented programming
OOP object oriented programming

AOP:基于OOP基础之上的新的编程思想
    指 在程序运行期间 将某段代码动态的切入到指定方法的指定位置 进行运行的这种编程方式

场景
    计算机进行计算方法的时候进行日志记录
    加日志记录
        直接编写在方法内部 不推荐修改维护麻烦 耦合度太高
        
        日志记录：系统的辅助功能
        业务逻辑：核心功能
我们希望的是：日志模块 在核心功能运行期间 自己动态的加上
运行的时候 日志的功能可以加上

可以使用动态代理将日志代码动态的在目标方法执行前后执行

动态代理：
    写起来难
    jdk默认的动态代理 如果目标对象没有实现任何接口 无法为他创建代理对象

Spring实现了AOP功能 
    底层就是动态代理
    可以利用Spring一句代码都不写地取创建动态代理
    实现简单 没有错强制要求目标对象必须实现接口

将某段代码动态的切入（不把日志代码谢斯在业务逻辑方法中）到指定方法（加减乘除）的指定位置（方法的开始 结束 一场）进行运行的这种编程方式

Spring简化了面向切面编程


AOP使用步骤
    导包
    写配置
        将目标类和切面类（封装了通知方法（在目标方法前后执行的方法））加入到ioc容器中 
        告诉spring哪一个是切面类@Aspect
        告诉spring 切面类的每一个方法 何时何地运行
        eg:
            @Before("execution(public int ljh.impl.MyCalculator.*(*))")
            public static void logStart(Method method,Object... args){
            System.out.println(method.getName()+" 方法开始执行 用的是参数列表："+ Arrays.asList(args));
            }
        开启基于注解的AOP模式
    测试

AOP的场景
    AOP加日志保存到数据库
    AOP做权限验证
    AOP做安全检查
    AOP做事务控制