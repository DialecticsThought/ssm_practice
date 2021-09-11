package liu.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Service(value = "userService_TX")
public class UserService {
    @Autowired
    UserDao userDao;
    @Transactional
    public void insertUser(){
        userDao.insert();
        //正常的事务的话 还有otherDao.xxx()方法
        System.out.println("插入完成....");
        //模拟事务
        int i=1/0;
    }

}
