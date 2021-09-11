package ljh.test;

import ljh.bean.Employee;
import ljh.dao.EmployeeDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class EmployeeTest {
    //工厂只要一个 session要创建多个
    SqlSessionFactory sqlSessionFactory;
    @Before//表示test之前运行
    public void initSqlSessionFactory() throws IOException {
        //1.根据全局配置文件创建出一个sqlSessionFactory
        //sqlSessionFactory 用来创建sqlSession对象
        // sqlSession对象 代表一个和数据库的对话
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        //4.调用dao方法
        Employee empById = employeeDao.getEmpById(1);
        System.out.println(empById);
    }
    @Test
    public void testInsert(){
        //2.获取数据库对话
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //3.获取接口的映射器
        EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
        //调用dao方法
        int tomcat = mapper.insertEmployee(new Employee(null, "tomcat", 1, "admin@qq.com"));

        System.out.println(tomcat);
        //提交之前的操作
        sqlSession.commit();
        //关闭资源
        sqlSession.close();
    }
}
