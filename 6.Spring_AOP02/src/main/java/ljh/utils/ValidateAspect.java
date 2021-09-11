package ljh.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
/*@Component
@Aspect
@Order(value = 1)*///改变切面的顺序 数值越小 越优先
public class ValidateAspect {
    //想在执行目标方法之前运行 写切入表达式 指定在哪个方法上执行
    //@Before("execution(public int ljh.impl.My*.*(*,*))")
    public static void logStart(JoinPoint joinPoint){
        //获取目标方法运行时候的使用的参数
        Object[] args = joinPoint.getArgs();
        //获取方法的签名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("Validate的前置 "+name+"方法开始执行 用的是参数列表："+ Arrays.asList(args));
    }
    //@AfterReturning(value = "ljh.utils.LogUtils.pointCut()",returning ="result")
    public static void logReturn(JoinPoint joinPoint, Object result){
        //获取目标方法运行时候的使用的参数
        Object[] args = joinPoint.getArgs();
        //获取方法的签名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("Validate的返回 "+name+"方法执行后 结果是："+result);
    }
    //@AfterThrowing(value = "execution(public int ljh..*(int,int))",throwing ="e")
    public static void logException(JoinPoint joinPoint,Exception e){
        //获取目标方法运行时候的使用的参数
        Object[] args = joinPoint.getArgs();
        //获取方法的签名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("Validate的异常 "+name+"方法执行出现异常 "+e);
    }
    //@After("execution(public int ljh.impl..*(int,int))")
    public static void logFinal(JoinPoint joinPoint){
        //获取目标方法运行时候的使用的参数
        Object[] args = joinPoint.getArgs();
        //获取方法的签名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("Validate的后置 "+name+"方法结束了");
    }
}
