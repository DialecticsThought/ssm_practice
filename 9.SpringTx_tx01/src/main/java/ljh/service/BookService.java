package ljh.service;

import ljh.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    @Autowired
    BookDao bookDao;
    /*
    * �����ĸ��û��ı���
    * */
    @Transactional
    public void checkout(String username,String isbn){
        //�����
        bookDao.updateStock(isbn);
        //��۸�
        int price = bookDao.getPrice(isbn);
        //�����
        bookDao.updateBalance(username,price);
    }
}
