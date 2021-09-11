package ljh.dao;

import ljh.bean.Key;
import ljh.bean.Lock;

import java.util.List;

public interface KeyDao {
    /*
    * 将钥匙和锁一起查出
    */
    public Key getKeyById(Integer id);
    //分布查询钥匙 id是钥匙的id
    public Key getKeyByIdInSimpleWay(Integer id);
    //根据锁的id查出所有钥匙 id是锁的id
    public List<Key> getKeyByLockId(Integer id);
}
