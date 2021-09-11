package ljh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {
    @Autowired
    private BookService bookService;
    @Transactional
    public void mulService(){

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

        //updatePrice������Ļ� checkoutҪ�ع�ô ���ǿ������õ�ͨ��������Ϊ���ǲ��Ǻ�֮ǰ�Ĵ�������һ������ Ҳ����ʹ��ͬһsql���ӣ�
        bookService.checkout("Tom","ISBN-001");

        bookService.updatePrice("ISBN-002",998);

        int i=10/0;
    }
}
