package liu.dao;

import org.springframework.stereotype.Repository;

//bean对象的名字是默认类名的首字母小写
@Repository
public class BookDao {

    private String lable="2";

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "lable='" + lable + '\'' +
                '}';
    }
}
