package liu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/*
 * 切面类
 * 告诉容器哪一个是切面类  @Aspect
 * */
@Aspect
public class LogAspects {
    //抽取公共的切入点表达式
    //本类引用
    //其他类引用 写全名
    @Pointcut("execution(* liu.aop.MathCalculator.*(..))")
    public void point() {
    }

    @Before("point()")
    public void logStart(JoinPoint joinPoint) {
        //得到运行时的参数
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature() + "除法运行。。参数列表是：{" + Arrays.asList(args) + "}");
    }

    @After("point()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "除法结束。。。");
    }

    //result用来接收返回值
    @AfterReturning(value = "point()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName() + "除法正常返回。。。运行结果是：{" + result + "}");
    }

    //exception用来接收异常
    @AfterThrowing(value = "point()", throwing = "exception")
    public void logExcetion(JoinPoint joinPoint,Exception exception) {
        System.out.println(joinPoint.getSignature().getName() + "除法异常。。。异常信息：{" + exception + "}");
    }
}
