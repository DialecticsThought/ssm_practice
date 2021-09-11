package ljh.service;

import ljh.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceExt extends BookService{
    @Autowired//自动装配 自动为这个属性赋值
    private BookDao bookDao;
}
