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
    * 传入哪个用户哪本书
    * */
    @Transactional
    public void checkout(String username,String isbn){
        //减库存
        bookDao.updateStock(isbn);
        //查价格
        int price = bookDao.getPrice(isbn);
        //减余额
        bookDao.updateBalance(username,price);
    }
}
