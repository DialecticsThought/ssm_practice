package liu.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//默认加在ioc容器中的组件 容器启动时会调用无参构造器创建对象 在进行初始化赋值等操作
@Component
public class Boss {

    private Car car;

    public Boss() {
    }
    @Autowired//放在构造器方法上/放在形参上   使用的参数 自定义类型的值 从 ioc容器中获取
    public Boss(/*@Autowired*/ Car car) {
        this.car = car;
        System.out.println("调用boss的有参构造器");
    }

    public Car getCar() {
        return car;
    }
    //@Autowired
    //标注在方法 时候 Spring容器创建当前对象 就会调用该方法 完成赋值
    //方法使用的参数 自定义类型的值 从 ioc容器中获取
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
