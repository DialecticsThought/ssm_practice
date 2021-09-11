package ljh.factory;

import ljh.bean.Book;
import org.hamcrest.Factory;
import org.springframework.beans.factory.FactoryBean;

/*实现了FactoryBean都是工厂类
   spring会自动地调用工厂方法创建实例

   1.编写一个FactoryBean实现类
   2.在spring配置文件中注册

   3.ioc启动的时候不会返回实例 不管是单例还是多例★★★★★★★
   4.在获取的时候才创建对象
* */
public class MyFactoryBeanImpl implements FactoryBean<Book> {
    /*
    * getObject()工厂方法
    * 返回创建对象
     *
    * */
    @Override
    public Book getObject() throws Exception {
        System.out.println("MyFactoryBeanImpl   帮你创建对象");
        Book book = new Book();
        book.setBookName("aaa");
        return book;
    }
    /*getObjectType()
    * 返回创建的对象的类型
    * Spring会自动调用这个方法来确认创建地对象是什么类型
    * */
    @Override
    public Class<?> getObjectType() {
        return Book.class;
    }
}
