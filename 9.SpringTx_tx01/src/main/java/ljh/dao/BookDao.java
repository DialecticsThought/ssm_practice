package ljh.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    /*
    * 减余额
    * */
    public void updateBalance(String username,int price){
        String sql="update account set balance=balance -? where username = ?";

        jdbcTemplate.update(sql,price,username);
    }
    /*
    * 获取某本图书的价格
    * */
    public int getPrice(String isbn){
        String sql="select price from book where isbn = ?";

        return jdbcTemplate.queryForObject(sql,Integer.class,isbn);
    }
    /*
    * 减库存 为了简单每次减一
    * */
    public void updateStock(String stock){
        String sql = "update book_stock set stock = stock - 1 where isbn = ?";

        jdbcTemplate.update(sql,stock);
    }


}
