package ljh.service;

import ljh.bean.User;
import ljh.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User> {
    @Autowired
    UserDao userDao;

    public void save(){
        userDao.save();
    }
}
