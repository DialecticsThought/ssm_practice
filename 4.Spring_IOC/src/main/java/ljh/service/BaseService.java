package ljh.service;

import ljh.bean.Book;
import ljh.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

//����д@Service��Ϊ�м̳й�ϵ �����Ҳ�ᱻ����bean��ע��
public class BaseService<T> {
    @Autowired
    private BaseDao<T> baseDao;

    public void save(){
        System.out.println("�Զ�ע��dao"+baseDao);
        baseDao.save();
    }
}
