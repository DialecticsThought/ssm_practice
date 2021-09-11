package ljh.proxy;

import ljh.inter.Calculator;
import ljh.utils.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

//帮Caculator生成代理对象的类
public class CalculatorProxy {

    /*
    * 为传入的参数对象创建一个动态代理对象
    * 传入的是被代理的对象
    * */
    //内部类要调用外面的参数 加上final
    public static Calculator getProxy(final Calculator calculator){
        //被代理对象的类加载器
        ClassLoader classLoader = calculator.getClass().getClassLoader();
        //被代理对象的接口
        Class<?>[] interfaces = calculator.getClass().getInterfaces();
        //方法执行器 帮我们目标对象执行目标方法
        InvocationHandler h = new InvocationHandler() {
            @Override
            /*Object proxy:代理对象 给jdk使用 任何时候都不要动这个对象
            *Method method：将要执行的目标对象的方法
            *Object[] args:这个方法调用是外界传入的参数值
            * */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("这是动态代理 代理执行的方法");
                Object result = null;
                try {
                    //方法执行前
                    //System.out.println(method.getName()+" 方法开始执行 用的是参数列表："+ Arrays.asList(args));
                    LogUtils.logStart(method,args);
                    //利用反射执行目标方法
                    //invoke是目标方法执行后的返回值
                    result = method.invoke(calculator, args);
                    //方法执行后
                    //System.out.println(method.getName()+" 方法执行结束 结果是："+result);
                    LogUtils.logEnd(method,result);
                }catch (Exception e){
                    LogUtils.logException(method,e);
                }finally {
                    LogUtils.logFinal(method);
                }

                //返回值必须返回出去外界才能拿到真正执行的返回值
                return result;
            }
        };
        Object proxy = Proxy.newProxyInstance(classLoader,interfaces,h);

        return (Calculator) proxy;
    }
}
