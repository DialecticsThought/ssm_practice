package ljh.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@Order(value = 2)//改变切面的顺序 数值越小 越优先
public class LogUtils {
    //Object... objects表示可变参数 不知道要穿多少个就写这个
/*    public static void logStart(Object... objects){
        System.out.println("开始计算 参数是+ "+objects);
    }*/

    /*
    * 如何将这个类（切面类）中的这些方法（通知/增强）在目标方法运行的各个位置中切入
    *
    * 告诉Spring每个方法都什么时候运行
    *
    *
    * @Before在执行目标方法之前运行 前置通知
    * @After()在执行目标方法正常执行之后运行   后置通知
    * @AfterReturning在执行目标方法正常执行并返回值之后运行 返回通知
    * @AfterThrowing在执行目标方法执行异常时运行  异常通知
    * @Around 环绕
    *
    * :细节2：
    * 切入点表达式
    * 固定格式： execution（访问权限符 返回值类型 方法的全类名(参数表)）
    *   通配符 *
    *           匹配一个/多个字符 eg:execution(public int ljh.impl.My*.*(int,int))
    *           匹配任意一个参数 eg:execution(public int ljh.impl.My*.*(*,*))
    *           只能匹配一层路径
    *           权限位置 * 不能使用
    *   .. 匹配任意一个/多个 任意类型参数
    *      匹配任意多层路径
    *
    *   不写权限位置不写的话就是默认public☆☆☆☆☆☆☆☆
    *
    *  &&切入的位置要满足两个表达式
    *       execution(public int ljh.impl.My*.*(*,*))&&execution(* *.*(int,int))
    *  ||满足任意一个表达式
    *  ! 只要不是这个表达式都切入
    * */

    /*
     * 细节4：
     * 在通知方法运行的时候 拿到目标方法的详细信息
     * 只需要为通知方法的参数列表上写一个参数
     * JoinPoint joinPoint:封装了当前目标方法的详细信息
     *
     * 细节5：
     * 告诉Spring result是用来接收返回值：@AfterReturning(returning ="result")
     * 告诉Spring result是用来接收异常：@AfterThrowing(returning ="result")
     *
     * 细节6：
     *  Spring对通知方法的要求不严格
     *  唯一的要求是方法的参数列表一定不能够乱写
     *  Sprng帮助调用通知方法是通过反射的 每一次方法调用得确定这个方法的参数表的值
     *  参数表的每一个参数 spring都要知道是什么
     *
     * Exception e ： 可以接受哪些异常 往大的范围写
     *
     * 抽取可重用的切入点表达式
     *  1.随便声明一个没有实现的返回void空方法
     *  2.标注@Pointcut
     *
     *
     * */
    /*
        条件：
    @Order(value = 1)//改变切面的顺序 数值越小 越优先
    * public class ValidateAspect
    *
    *  @Order(value = 2)//改变切面的顺序 数值越小 越优先
    *  public class LogUtils {
    *
    * 执行顺序：
    *  Validate的前置 add方法开始执行 用的是参数列表：[2, 1]
    *  LogUitls的环绕的前置通知 add方法开始
    *  LogUtils的前置 add方法开始执行 用的是参数列表：[2, 1]
    *  调用了add方法 结果是3
    *  LogUtils的返回 add方法执行后 结果是：3
    *  LogUtils的后置 add方法结束了
    *  LogUitls的环绕的返回通知 add返回值： 3
    *  LogUitls的环绕后置通知add 方法结束
    *  Validate的返回 add方法执行后 结果是：3
    *  Validate的后置 add方法结束了
    *
    * */
    @Pointcut("execution(public int ljh.impl.MyCalculator.*(..))")
    public void pointCut(){ }

    //想在执行目标方法之前运行 写切入表达式 指定在哪个方法上执行
    @Before("execution(public int ljh.impl.My*.*(*,*))")
    public static void logStart(JoinPoint joinPoint){
        //获取目标方法运行时候的使用的参数
        Object[] args = joinPoint.getArgs();
        //获取方法的签名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils的前置 "+name+"方法开始执行 用的是参数列表："+Arrays.asList(args));
    }
    /*
    public static void logStart(Method method,Object... args){
        System.out.println(method.getName()+" 方法开始执行 用的是参数列表："+ Arrays.asList(args));
    }*/
    //想在执行目标方法正常执行之后运行
    /*
    * 告诉Spring result是用来接收返回值：@AfterReturning(returning ="result")
    * */
   @AfterReturning(value = "pointCut()",returning ="result")
    public static void logEnd(JoinPoint joinPoint,Object result){
       //获取目标方法运行时候的使用的参数
       Object[] args = joinPoint.getArgs();
       //获取方法的签名
       Signature signature = joinPoint.getSignature();
       String name = signature.getName();
        System.out.println("LogUtils的返回 "+name+"方法执行后 结果是："+result);
    }
    /*
    public static void logEnd(Method method,Object...result){
        System.out.println(method.getName()+" 方法执行后 结果是："+Arrays.asList(result));
    }*/
    //想在执行目标方法执行异常时运行
    /*
     * 告诉Spring result是用来接收异常：@AfterThrowing(returning ="result")
     * */
    @AfterThrowing(value = "execution(public int ljh..*(int,int))",throwing ="e")
    public static void logException(JoinPoint joinPoint,Exception e){
        //获取目标方法运行时候的使用的参数
        Object[] args = joinPoint.getArgs();
        //获取方法的签名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils的异常 "+name+"方法执行出现异常 "+e);
    }
    /*public static void logException(Method method,Exception e){
        System.out.println(method.getName()+" 方法执行出现异常 "+e);
    }*/
    //想在执行目标方法结束的时侯运行
    @After("execution(public int ljh.impl..*(int,int))")
    public static void logFinal(JoinPoint joinPoint){
        //获取目标方法运行时候的使用的参数
        Object[] args = joinPoint.getArgs();
        //获取方法的签名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils的后置 "+name+"方法结束了");
    }
    /*public static void logFinal(Method method){
        System.out.println(method.getName()+" 方法结束了");
    }*/

    /*
     * @Around是spring中最强大的通知 相当于动态代理
     *   动态代理最核心的一句话 method.invoke(obj,args)
     * try{
     *   //迁址通知
     *    method.invoke(obj,args)
     *   //返回通知
     * }catch(e){
     *   //异常通知
     * }finally{
     *   //后置通知
     * }
     *
     * 四合一通知就是环绕通知
     * 环绕通知中有一个参数 ProceedingJoinPoint
     *
     * 环绕通知：优先于普通通知执行 执行顺序
     *
     *  环绕前置
     * 【普通前置】
     *  目标执行
     * 【普通方法返回/方法异常】
     * 【普通后置】
     *  环绕返回
     *  环绕后置
     * */
    @Around(value = "pointCut()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        //得到方法名
        String name = proceedingJoinPoint.getSignature().getName();
        Object proceed = null;
        try {
            //@Before("execution(public int ljh.impl.My*.*(*,*))")
            System.out.println("LogUitls的环绕的前置通知 "+name+"方法开始");
            //就是利用反射调用目标方法 proceedingJoinPoint.proceed(args)<===> method.invoke(obj,args)
            proceed = proceedingJoinPoint.proceed(args);
            //@AfterReturning(value = "pointCut()",returning ="result")
            System.out.println("LogUitls的环绕的返回通知 "+name+"返回值： "+proceed);
        }catch (Exception e){
            //相当于异常通知 @AfterThrowing(value = "execution(public int ljh..*(int,int))",throwing ="e")
            e.printStackTrace();
            System.out.println("LogUitls的环绕异常通知 "+name+"异常信息： "+e);
        }finally {
            //@After("execution(public int ljh.impl..*(int,int))")
            System.out.println("LogUitls的环绕后置通知"+name+" 方法结束");
        }
        //System.out.println("around.....");

        //利用反射后的返回值也一定返回出去
        return proceed;
    }
}
