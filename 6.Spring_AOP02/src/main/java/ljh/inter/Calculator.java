package ljh.inter;

/*
* 接口不加载容器中
*   实际上加了也不会创建对象 只要是这个组件为一个接口
* 相当于告诉Spring ioc容器中有这种类型的组件
*
* */
public interface Calculator {

    public int add(int i,int j);

    public int sub(int i,int j);

    public int mul(int i,int j);

    public int div(int i,int j);
}
