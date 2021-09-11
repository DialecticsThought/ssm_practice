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
        //1.����ȫְ�����ļ�������һ��sqlSessionFactory
        //sqlSessionFactory ��������sqlSession����
        // sqlSession���� ����һ�������ݿ�ĶԻ�
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.��ȡ�����ݿ��һ�ζԻ�getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.ʹ��sqlSession�����ݿ⽻�� ��ȡdao�ӿڵ�ʵ��
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        //4.����dao����
        Employee empById = employeeDao.getEmpById(1);
        System.out.println(empById);

    }
}
