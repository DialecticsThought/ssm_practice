package ljh.test;

import ljh.service.BookService;
import ljh.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    @org.junit.Test
    public void test01(){
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");
        BookService bookSerive = ioc.getBean("bookService", BookService.class);
        UserService userService = ioc.getBean("userService", UserService.class);

        bookSerive.save();
        userService.save();
        //得到父类类型
        System.out.println(bookSerive.getClass().getSuperclass());
        //得到带泛型的父类类型
        System.out.println(bookSerive.getClass().getGenericSuperclass());
    }
}
