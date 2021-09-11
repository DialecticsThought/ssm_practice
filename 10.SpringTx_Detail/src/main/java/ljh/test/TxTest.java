package ljh.test;

import ljh.service.BookService;
import ljh.service.MulService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TxTest {
    /*
    * �������ҵ���߼� �����б��������ҵ���߼��Ĵ������
    * ������� ͨ���������BookService�ķ���
    * */
    @Test
    public void test(){
        ClassPathXmlApplicationContext tx = new ClassPathXmlApplicationContext("tx.xml");

        BookService bean = tx.getBean(BookService.class);

        bean.checkout("Tom","ISBN-001");

        int price = bean.getPrice("ISBN-001");

        System.out.println("������"+price);

        System.out.println("�������");

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
        //Ч����û�ģ��൱�ڻع��� ��ȻmulTx���������������³�
        bean.mulTx();

        System.out.println(bean.getClass());
        /*
        * �����MulService --mulTx��������bookeService��������
        * bookService --mulTx������������������
        *
        * MulServiceProxy.mulTx(){
        *   bookService�Ĵ������.checkout()
        * bookService�Ĵ������.updatePrice()
        * }
        *
        * ���෽����Ƕ����һ������
        *BookServiceProxy.mulTx(){
        *   checkout()
        *   updatePrice()
        *   //�൱�ڰ�checkout()��updatePrice()�Ĵ��븴�ƽ��� �ϳ�һ������
        * }
        * */
    }
}
