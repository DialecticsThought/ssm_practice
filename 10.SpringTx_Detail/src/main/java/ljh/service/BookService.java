package ljh.service;

import ljh.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    @Autowired
    BookDao bookDao;
    /*
���checkout��updatePrice  ����Propagation.REQUIRED ����һ������
����  MulService���� mulService������������е����񶼻ع�

���checkout Propagation.REQUIRES_NEW  updatePrice  Propagation.REQUIRED
    ˵�� checkout ������  updatePrice��MulService����һ������
    �����Ƕ����� ����һ���� һ���ɹ� ����һ��ʧ��û�й�ϵ
*/
    /*
     * multi(){
     *       //REQUIRED
     *       A(){
     *           //REQUIRES_NEW
     *           B(){
     *           }
     *           //REQUIRED
     *           C(){
     *
     *           }
     *       }
     *       //REQUIRES_NEW
     *       D(){
     *           //REQUIRED
     *           E(){
     *               //REQUIRES_NEW
     *               F(){
     *                  10/0
     *               }
     *           }
     *           //REQUIRES_NEW
     *           G(){
     *           }
     *       }
     *   int 10/0;
     *
     * }
     * �������A������һ������ A�����B���񵥶�һ�� B�ܳɹ�  C��Aһ������==>���ܳɹ�
     *D����ʹ�������һ������ D�ܳɹ� E��Dͬһ������ ��ȻF��D����һ�����ﵫ�� F�ܳɹ�  G�ʹ�������һ������
     *
     * F���� ����Fû��try/catch ��ʹF��REQUIRES_NEW�����쳣һ��һ������ӳ�� EҲ���� ���벻������ִ�� GҲ��ִ��
     * A���ˣ��ʹ�����ͬһ���� c���ˣ���Aͬһ������ B�Ѿ�ִ���˲���REQUIRES_NEWһ������� ��������
     *
     *
     * ���������REQUIRE�Ļ� ���е�����eg�� timeout�ȶ���ȡ���ڴ����� REQUIRES_NEW����ȡ�����Լ����������������
     *
     *
     * REQUIRE��֮ǰ�����õ�connection���ݸ��������ʹ�á�������
     * REQUIRES_NEW ֱ�����µ�connection��������
     *
     * */
    /*
    * �����ĸ��û��ı���
    *
    * �����ϸ��
    * isolation-Isolation����ĸ��뼶��
    * propagation-Propagation������Ϊ
    *   ������Ϊ = ����Ĵ��� +�������Ϊ
    *       ����ж���������Ƕ������ �������Ƿ�Ҫ�ʹ�������һ������
    * eg: A��Bȥ���� 2��ѡ��
    *           A��B����һ����ȥ����
    *           B��A���Կ���ȥ����
    * require = B�г��Ļ� A�Ͳ����ĳ� ���� �Լ����� �����һ������������ ��ǰ�ķ��������������������
    *                                               ���� �Ϳ���һ���µ����� ���Լ�������������
    * require_new = B �� A �����Եĳ�  ��ǰ�ķ������뿪�������� �����Լ�������������
    *                                           ��������������� Ӧ�ý���������ͣ��
    * supports B�г��Ļ� A�Ͳ����ĳ� ���� B��A��û���Ͳ�ȥ������    ��������������� ��ǰ�ķ�������������������� ���������Բ�������������
    * not_support ��ǰ�ķ�����Ӧ�������������� ��������е����� ��������
    * mandatory ��ǰ�ķ������������������ڲ� ���û���������е����� �����쳣����support��ͬ�������쳣��
    * never ��ǰ�ķ�����Ӧ�������������� ��������е����� �����쳣����not_support��ͬ�����쳣��
    * AService{
    *       tx_a(){
    *           //a����һЩ����
    *           tx_b(){
    *               }
    *           tx_c(){
    *               }
    *       }
    * }
    *
    *
    * noRollBackFor-Class[] ��Щ�����쳣���Բ��ع�����Ĭ�ϻع����쳣���ع���
    * noRollbackForClassName-String[] дȫ����
    * RollbackFor-Class[] ��Щ�쳣������Իع�(��Ա���ʱ�쳣 ��ΪĬ�ϱ���ʱ�쳣�ǲ��ع���)
    * RollbackForClassName-String[]
    * �쳣��
    *       ����ʱ�쳣��
    *           ���Բ��ô���
    *       ����ʱ�쳣��
    *           1.try/catch Ĭ�ϻع�
    *           2.����������throw Ĭ�ϲ��ع�
    *
    * ����Ļع���Ĭ�Ϸ�������ʱ�쳣���ع� ��������ʱ�쳣���ع�
    *
    * readOnly-boolean ��������Ϊֻ������
    *       ���������Ż����ӿ��ѯ�ٶ� ���ù�������һ�Ѳ���
    *       ��������޸�����ɾ����Ӹò���
    * timeout-int����Ϊ��λ�� ��ʱ�����񳬳�ָ��ִ��ʱ�����Զ���ֹ �ع�
    * */
    @Transactional(timeout = 3,noRollbackFor = {ArithmeticException.class},propagation = Propagation.REQUIRES_NEW)
    public void checkout(String username,String isbn){
        //�����
        bookDao.updateStock(isbn);
/*        try {
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        //��۸�
        int price = bookDao.getPrice(isbn);
        //�����
        bookDao.updateBalance(username,price);
        //ArithmeticException.class
        //int i = 10/0;
    }
    //readOnly = true�ӿ��ѯ�ٶ� isolation = Isolation.READ_COMMITTEDҪ��Transaction01ֻ�ܶ�ȡTransaction02���ύ���޸�
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    public int getPrice(String isbn){
       return bookDao.getPrice(isbn);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePrice(String isbn,int price){
        bookDao.updatePrice(isbn,price);
    }


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
    @Transactional
    public void mulTx(){
        checkout("Tom","ISBN-001");

        updatePrice("ISBN-002",998);
    }
}
