package ljh.bean;

import java.util.List;

public class Lock {
    private Integer id;
    private String lockName;

    //相同的锁有好几个钥匙
    //一个锁有好几个钥匙 多对一关系
    //外键放在多的那一方
    //多对多关系的外键是放在中间表
    private List<Key> keys;

    public Lock() {
    }

    public List<Key> getKeys() {
        return keys;
    }

    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "id=" + id +
                ", lockName='" + lockName + '\'' +
                '}';
    }
}
