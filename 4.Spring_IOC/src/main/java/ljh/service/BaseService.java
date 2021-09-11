package ljh.service;

import ljh.bean.Book;
import ljh.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

//不用写@Service因为有继承关系 这个类也会被创建bean和注入
public class BaseService<T> {
    @Autowired
    private BaseDao<T> baseDao;

    public void save(){
        System.out.println("自动注入dao"+baseDao);
        baseDao.save();
    }
}
