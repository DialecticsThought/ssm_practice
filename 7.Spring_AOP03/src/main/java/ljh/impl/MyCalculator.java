package ljh.impl;

import org.springframework.stereotype.Service;


public class MyCalculator /*implements Calculator */{
    //@Override
    public int add(int i, int j) {
        //System.out.println("������add���� ������+"+i+"+"+j);
        //LogUtils.logStart(i,j);
        int result =i + j;
        System.out.println("������add���� �����"+result);
        return result;
    }

    //@Override
    public int sub(int i, int j) {
        //System.out.println("������sub���� ������+"+i+"+"+j);
        //LogUtils.logStart(i,j);
        int result = i - j;
        System.out.println("������sub���� �����"+result);
        return result;
    }

    //@Override
    public int mul(int i, int j) {
        //System.out.println("������mul���� ������+"+i+"+"+j);
        //LogUtils.logStart(i,j);
        int result = i * j;
        System.out.println("������mul���� �����"+result);
        return result;
    }

    //@Override
    public int div(int i, int j) {
        //System.out.println("������div���� ������+"+i+"+"+j);
        //LogUtils.logStart(i,j);
        int result = i / j;
        System.out.println("������div���� �����"+result);
        return result;
    }
}
