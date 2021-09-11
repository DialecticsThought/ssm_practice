package ljh.utils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogUtils {
    //Object... objects表示可变参数 不知道要穿多少个就写这个
/*    public static void logStart(Object... objects){
        System.out.println("开始计算 参数是+ "+objects);
    }*/

    /*
    * 如何将这个类（切面类）中的这些方法（通知/增强）在目标方法运行的各个位置中切入
    * */
    public static void logStart(Method method,Object... args){
        System.out.println(method.getName()+" 方法开始执行 用的是参数列表："+ Arrays.asList(args));
    }

    public static void logEnd(Method method,Object...result){
        System.out.println(method.getName()+" 方法执行后 结果是："+Arrays.asList(result));
    }

    public static void logException(Method method,Exception e){
        System.out.println(method.getName()+" 方法执行出现异常 "+e);
    }

    public static void logFinal(Method method){
        System.out.println(method.getName()+" 方法结束了");
    }
}
