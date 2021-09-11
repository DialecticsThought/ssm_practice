package ljh.test;

import ljh.service.BookService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TxTest {

    @Test
    public void test(){
        ClassPathXmlApplicationContext tx = new ClassPathXmlApplicationContext("tx.xml");

        BookService bean = tx.getBean(BookService.class);

        bean.checkout("Tom","ISBN-001");

        System.out.println("Ω·’ÀÕÍ≥…");

    }
}
