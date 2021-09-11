package ljh.impl;

import ljh.inter.Calculator;
import ljh.utils.LogUtils;

public class MyCalculator implements Calculator {
    @Override
    public int add(int i, int j) {
        //System.out.println("调用了add方法 参数是+"+i+"+"+j);
        //LogUtils.logStart(i,j);
        int result =i + j;
        System.out.println("调用了add方法 结果是"+result);
        return result;
    }

    @Override
    public int sub(int i, int j) {
        //System.out.println("调用了sub方法 参数是+"+i+"+"+j);
        //LogUtils.logStart(i,j);
        int result = i - j;
        System.out.println("调用了sub方法 结果是"+result);
        return result;
    }

    @Override
    public int mul(int i, int j) {
        //System.out.println("调用了mul方法 参数是+"+i+"+"+j);
        //LogUtils.logStart(i,j);
        int result = i * j;
        System.out.println("调用了mul方法 结果是"+result);
        return result;
    }

    @Override
    public int div(int i, int j) {
        //System.out.println("调用了div方法 参数是+"+i+"+"+j);
        //LogUtils.logStart(i,j);
        int result = i / j;
        System.out.println("调用了div方法 结果是"+result);
        return result;
    }
}
