package ljh.dao;

import ljh.bean.Lock;

public interface LockDao {
    //查梭子的时候将所有的钥匙也查出来
    public Lock getLockById(Integer id);

    public Lock getLockByIdInSimpleWay(Integer id);
    //分布查询锁 针对的是collection集合
    public Lock getLockByStep(Integer id);
}
