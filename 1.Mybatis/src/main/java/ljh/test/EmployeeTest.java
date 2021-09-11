package ljh.test;

import ljh.bean.Employee;
import ljh.dao.EmployeeDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;

public class EmployeeTest {

    @Test
    public void test() throws IOException {
        //1.根据全职配置文件创建出一个sqlSessionFactory
        //sqlSessionFactory 用来创建sqlSession对象
        // sqlSession对象 代表一个和数据库的对话
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        //4.调用dao方法
        Employee empById = employeeDao.getEmpById(1);
        System.out.println(empById);

    }
}
