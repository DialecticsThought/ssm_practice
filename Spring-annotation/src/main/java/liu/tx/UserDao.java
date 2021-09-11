package liu.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(){
        String SQL="insert into tbl_user (username,age) values(?,?)";

        String username= UUID.randomUUID().toString().substring(0,5);

        jdbcTemplate.update(SQL,username,19);

    }
}
