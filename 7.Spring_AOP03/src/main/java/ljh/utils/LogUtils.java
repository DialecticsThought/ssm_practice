package ljh.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;


public class LogUtils {
    //Object... objects��ʾ�ɱ���� ��֪��Ҫ�����ٸ���д���
/*    public static void logStart(Object... objects){
        System.out.println("��ʼ���� ������+ "+objects);
    }*/

    /*
    * ��ν�����ࣨ�����ࣩ�е���Щ������֪ͨ/��ǿ����Ŀ�귽�����еĸ���λ��������
    *
    * ����Springÿ��������ʲôʱ������
    *
    *
    * @Before��ִ��Ŀ�귽��֮ǰ���� ǰ��֪ͨ
    * @After()��ִ��Ŀ�귽������ִ��֮������   ����֪ͨ
    * @AfterReturning��ִ��Ŀ�귽������ִ�в�����ֵ֮������ ����֪ͨ
    * @AfterThrowing��ִ��Ŀ�귽��ִ���쳣ʱ����  �쳣֪ͨ
    * @Around ����
    *
    * :ϸ��2��
    * �������ʽ
    * �̶���ʽ�� execution������Ȩ�޷� ����ֵ���� ������ȫ����(������)��
    *   ͨ��� *
    *           ƥ��һ��/����ַ� eg:execution(public int ljh.impl.My*.*(int,int))
    *           ƥ������һ������ eg:execution(public int ljh.impl.My*.*(*,*))
    *           ֻ��ƥ��һ��·��
    *           Ȩ��λ�� * ����ʹ��
    *   .. ƥ������һ��/��� �������Ͳ���
    *      ƥ��������·��
    *
    *   ��дȨ��λ�ò�д�Ļ�����Ĭ��public���������
    *
    *  &&�����λ��Ҫ�����������ʽ
    *       execution(public int ljh.impl.My*.*(*,*))&&execution(* *.*(int,int))
    *  ||��������һ�����ʽ
    *  ! ֻҪ����������ʽ������
    * */

    /*
     * ϸ��4��
     * ��֪ͨ�������е�ʱ�� �õ�Ŀ�귽������ϸ��Ϣ
     * ֻ��ҪΪ֪ͨ�����Ĳ����б���дһ������
     * JoinPoint joinPoint:��װ�˵�ǰĿ�귽������ϸ��Ϣ
     *
     * ϸ��5��
     * ����Spring result���������շ���ֵ��@AfterReturning(returning ="result")
     * ����Spring result�����������쳣��@AfterThrowing(returning ="result")
     *
     * ϸ��6��
     *  Spring��֪ͨ������Ҫ���ϸ�
     *  Ψһ��Ҫ���Ƿ����Ĳ����б�һ�����ܹ���д
     *  Sprng��������֪ͨ������ͨ������� ÿһ�η������õ�ȷ����������Ĳ������ֵ
     *  �������ÿһ������ spring��Ҫ֪����ʲô
     *
     * Exception e �� ���Խ�����Щ�쳣 ����ķ�Χд
     *
     * ��ȡ�����õ��������ʽ
     *  1.�������һ��û��ʵ�ֵķ���void�շ���
     *  2.��ע@Pointcut
     *
     *
     * */
    /*
        ������
    @Order(value = 1)//�ı������˳�� ��ֵԽС Խ����
    * public class ValidateAspect
    *
    *  @Order(value = 2)//�ı������˳�� ��ֵԽС Խ����
    *  public class LogUtils {
    *
    * ִ��˳��
    *  Validate��ǰ�� add������ʼִ�� �õ��ǲ����б�[2, 1]
    *  LogUitls�Ļ��Ƶ�ǰ��֪ͨ add������ʼ
    *  LogUtils��ǰ�� add������ʼִ�� �õ��ǲ����б�[2, 1]
    *  ������add���� �����3
    *  LogUtils�ķ��� add����ִ�к� ����ǣ�3
    *  LogUtils�ĺ��� add����������
    *  LogUitls�Ļ��Ƶķ���֪ͨ add����ֵ�� 3
    *  LogUitls�Ļ��ƺ���֪ͨadd ��������
    *  Validate�ķ��� add����ִ�к� ����ǣ�3
    *  Validate�ĺ��� add����������
    *
    * */

    //����ִ��Ŀ�귽��֮ǰ���� д������ʽ ָ�����ĸ�������ִ��
    public static void logStart(JoinPoint joinPoint){
        //��ȡĿ�귽������ʱ���ʹ�õĲ���
        Object[] args = joinPoint.getArgs();
        //��ȡ������ǩ��
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils��ǰ�� "+name+"������ʼִ�� �õ��ǲ����б�"+Arrays.asList(args));
    }
    /*
    public static void logStart(Method method,Object... args){
        System.out.println(method.getName()+" ������ʼִ�� �õ��ǲ����б�"+ Arrays.asList(args));
    }*/
    //����ִ��Ŀ�귽������ִ��֮������
    /*
    * ����Spring result���������շ���ֵ��@AfterReturning(returning ="result")
    * */
    public static void logEnd(JoinPoint joinPoint,Object result){
       //��ȡĿ�귽������ʱ���ʹ�õĲ���
       Object[] args = joinPoint.getArgs();
       //��ȡ������ǩ��
       Signature signature = joinPoint.getSignature();
       String name = signature.getName();
        System.out.println("LogUtils�ķ��� "+name+"����ִ�к� ����ǣ�"+result);
    }
    /*
    public static void logEnd(Method method,Object...result){
        System.out.println(method.getName()+" ����ִ�к� ����ǣ�"+Arrays.asList(result));
    }*/
    //����ִ��Ŀ�귽��ִ���쳣ʱ����
    /*
     * ����Spring result�����������쳣��@AfterThrowing(returning ="result")
     * */
    public static void logException(JoinPoint joinPoint,Exception e){
        //��ȡĿ�귽������ʱ���ʹ�õĲ���
        Object[] args = joinPoint.getArgs();
        //��ȡ������ǩ��
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils���쳣 "+name+"����ִ�г����쳣 "+e);
    }
    /*public static void logException(Method method,Exception e){
        System.out.println(method.getName()+" ����ִ�г����쳣 "+e);
    }*/
    //����ִ��Ŀ�귽��������ʱ������
    public static void logFinal(JoinPoint joinPoint){
        //��ȡĿ�귽������ʱ���ʹ�õĲ���
        Object[] args = joinPoint.getArgs();
        //��ȡ������ǩ��
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils�ĺ��� "+name+"����������");
    }
    /*public static void logFinal(Method method){
        System.out.println(method.getName()+" ����������");
    }*/

    /*
     * @Around��spring����ǿ���֪ͨ �൱�ڶ�̬����
     *   ��̬��������ĵ�һ�仰 method.invoke(obj,args)
     * try{
     *   //Ǩַ֪ͨ
     *    method.invoke(obj,args)
     *   //����֪ͨ
     * }catch(e){
     *   //�쳣֪ͨ
     * }finally{
     *   //����֪ͨ
     * }
     *
     * �ĺ�һ֪ͨ���ǻ���֪ͨ
     * ����֪ͨ����һ������ ProceedingJoinPoint
     *
     * ����֪ͨ����������ִ֪ͨͨ�� ִ��˳��
     *
     *  ����ǰ��
     * ����ͨǰ�á�
     *  Ŀ��ִ��
     * ����ͨ��������/�����쳣��
     * ����ͨ���á�
     *  ���Ʒ���
     *  ���ƺ���
     * */
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        //�õ�������
        String name = proceedingJoinPoint.getSignature().getName();
        Object proceed = null;
        try {
            //@Before("execution(public int ljh.impl.My*.*(*,*))")
            System.out.println("LogUitls�Ļ��Ƶ�ǰ��֪ͨ "+name+"������ʼ");
            //�������÷������Ŀ�귽�� proceedingJoinPoint.proceed(args)<===> method.invoke(obj,args)
            proceed = proceedingJoinPoint.proceed(args);
            //@AfterReturning(value = "pointCut()",returning ="result")
            System.out.println("LogUitls�Ļ��Ƶķ���֪ͨ "+name+"����ֵ�� "+proceed);
        }catch (Exception e){
            //�൱���쳣֪ͨ @AfterThrowing(value = "execution(public int ljh..*(int,int))",throwing ="e")
            e.printStackTrace();
            System.out.println("LogUitls�Ļ����쳣֪ͨ "+name+"�쳣��Ϣ�� "+e);
        }finally {
            //@After("execution(public int ljh.impl..*(int,int))")
            System.out.println("LogUitls�Ļ��ƺ���֪ͨ"+name+" ��������");
        }
        //System.out.println("around.....");

        //���÷����ķ���ֵҲһ�����س�ȥ
        return proceed;
    }
}
