package ljh.servlet;


import ljh.dao.BookDao;
import ljh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;


/*
* bean的id <===> 类名并且首字母小写
* 1.组建的id默认就是组件地类名并且首字母小写
* 2.组建的作用域 默认单例

        通过给bean添加某些注解 可以快速地将bean加入到ioc容器
        某个类上添加任何一个注解都能快速的将这个组件加入到ioc容器的管理中
        @Controller 控制器
        @Service 业务逻辑层
        @Repository dao层
        @Component 不属于以上几层的组件添加这个注解

        使用注解将租界快速地加入到容器中
            1.要给添加的组件上标四个注解的任何一个
            2.告诉spring 自动扫描加了注解的组件 依赖context名称空间
            3.一定要导入aop包 支持加注解模式地


    * 使用注解加入到容器中地组件 和使用配置加入到容器地组件行为都是默认一样的
    * 1.组建的id默认就是组件地类名并且首字母小写
    * 2.组建的作用域 默认单例
    *
    * @Autowired：Spring会自动的为这个属性赋值 不用担心空指针一场
    *   一定是去容器找到这个属性对应的组件（注解已经干了）
    * */
@Controller
public class BookServlet {
    @Qualifier("bookService")//指定一个名字作为id 让spring不用使用变量名作为id
    @Autowired(required = false)//自动装配 自动为这个属性赋值
    //private BookService bookService;
    private BookService bookServiceExt2;
    public void doGet(){
        bookServiceExt2.save();
    }


    //这个方法上的每一个参数都会自动注入值
    //这个方法会在bean创建的时候自动运行☆☆☆☆☆☆☆
    @Autowired
    public void hahaha(BookDao bookDao){
        System.out.println("spring运行了这个方法"+bookDao);
    }

}
