package ljh.test;

import ljh.service.BookService;
import ljh.service.MulService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TxTest {
    /*
    * 有事务的业务逻辑 容器中保存的是这业务逻辑的代理对象
    * 代理对象 通过反射调用BookService的方法
    * */
    @Test
    public void test(){
        ClassPathXmlApplicationContext tx = new ClassPathXmlApplicationContext("tx.xml");

        BookService bean = tx.getBean(BookService.class);

        bean.checkout("Tom","ISBN-001");

        int price = bean.getPrice("ISBN-001");

        System.out.println("读数据"+price);

        System.out.println("结账完成");

    }
    @Test
    public void test02(){
        ClassPathXmlApplicationContext tx = new ClassPathXmlApplicationContext("tx.xml");

        MulService bean = tx.getBean(MulService.class);

        bean.mulService();
    }

    @Test
    public void test03(){
        ClassPathXmlApplicationContext tx = new ClassPathXmlApplicationContext("tx.xml");

        BookService bean = tx.getBean(BookService.class);
        //效果是没改（相当于回滚） 虽然mulTx这两个方法都开新车
        bean.mulTx();

        System.out.println(bean.getClass());
        /*
        * 如果是MulService --mulTx（）调用bookeService两个方法
        * bookService --mulTx（）这届调用两个方法
        *
        * MulServiceProxy.mulTx(){
        *   bookService的代理对象.checkout()
        * bookService的代理对象.updatePrice()
        * }
        *
        * 本类方法的嵌套是一个事务
        *BookServiceProxy.mulTx(){
        *   checkout()
        *   updatePrice()
        *   //相当于把checkout()和updatePrice()的代码复制进来 合成一个事物
        * }
        * */
    }
}
