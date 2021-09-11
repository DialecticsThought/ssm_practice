package ljh.test;

import ljh.bean.Cat;
import ljh.bean.Employee;
import ljh.bean.Key;
import ljh.bean.Lock;
import ljh.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisTest {
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
        System.out.println(empById.toString());
    }
    @Test
    public void testInsert(){
        //true <=> sqlSession.commit();
        //2.获取数据库对话
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //3.获取接口的映射器
        EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);

        Employee employee = new Employee(null, "tomcat1", 1, "admin1@qq.com", null);
        //调用dao方法
        int employeeId = mapper.insertEmployee(employee);

        System.out.println(employee.getId());
        //提交之前的操作
        sqlSession.commit();
        //关闭资源
        sqlSession.close();
    }
    @Test
    public void testAnnotation(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeDaoAnnotation mapper = sqlSession.getMapper(EmployeeDaoAnnotation.class);
        Employee empById = mapper.getEmpById(1);
        System.out.println(empById.toString());
    }
    @Test
    public void testInsert2(){
        //true <=> sqlSession.commit();
        //2.获取数据库对话
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //3.获取接口的映射器
        EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);

        Employee employee = new Employee(null, "tomcat1", 1, "admin1@qq.com", null);
        //调用dao方法
        int employeeId = mapper.insertEmployee2(employee);

        System.out.println(employee.getId());
        //提交之前的操作
        sqlSession.commit();
        //关闭资源
        sqlSession.close();
    }

    @Test
    public void test2() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        //4.调用dao方法
        Employee admin = employeeDao.getEmpByIdAndEmpName(1, "admin");
        System.out.println(admin.toString());
    }

    @Test
    public void test3() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        //自定义一个map
        HashMap<String, Object> map = new HashMap<>();

        map.put("id",1);

        map.put("empname","admin");

        map.put("tableName","t_employee");
        //4.调用dao方法
        Employee admin = employeeDao.getEmpMAPByIdAndEmpName(map);
        System.out.println(admin.toString());
    }

    @Test
    public void test4() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        //4.调用dao方法
        List<Employee> allEmps = employeeDao.getAllEmps();

        System.out.println(allEmps.size());
    }

    @Test
    public void test5() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        //4.调用dao方法
        Map<String, Object> empByIdReturnMap = employeeDao.getEmpByIdReturnMap(1);

        System.out.println(empByIdReturnMap.toString());
    }


    @Test
    public void test6() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
        //4.调用dao方法
        Map<Integer, Employee> allEmpReturnMap = employeeDao.getAllEmpReturnMap();

        System.out.println(allEmpReturnMap);
    }
    /*
    * 默认mybatis自动封装结果集
    * 按照列名和属性名  一一对应的规则（不区分大小写）
    * 如果不一一对应
    *   开启驼峰命名法（满足其规则 eg:aaa_bbb  aaaBbb）
    *
    *
    * */

    @Test
    public  void test7() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        CatDao mapper = sqlSession.getMapper(CatDao.class);
        //4.调用dao方法
        Cat catById = mapper.getCatById(1);

        System.out.println(catById.toString());
    }
    /*
     *
     * 级联查询情况下
     *   使用级联属性封装联合查询后的所有结果
     *  <resultMap id="key" type="ljh.bean.Key">
            <id property="id" column="id"/>
            <result property="keyName" column="keyname"/>
            <result property="lock.id" column="id"/>
            <result property="lock.lockName" column="lockName"/>
        </resultMap>
     *
     * */
    @Test
    public  void test8() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        KeyDao mapper = sqlSession.getMapper(KeyDao.class);
        //4.调用dao方法
        Key keyById = mapper.getKeyById(1);

        System.out.println(keyById.toString());
    }

    @Test
    public  void test9() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        LockDao mapper = sqlSession.getMapper(LockDao.class);
        //4.调用dao方法
        Lock lockById = mapper.getLockById(1);

        System.out.println(lockById.toString());
        //获取该锁的所有钥匙
        List<Key> keys = lockById.getKeys();
        for (Key key:keys){
            System.out.println(key);
        }

    }
    /*
    *
    * 分布查询
    * 查询钥匙的时候顺便查出锁
    * Key key = KeyDao.getKeyById(1)
    * Lock lock = lockDao.getLockById(1)
    *
    * mybatis-config.xml
    *   <settings>
    *     <setting name="mapUnderscoreToCamelCase" value="true"/>
    *         <!--开启延迟加载-->
    *         <setting name="lazyLoadingEnabled" value="true"/>
    *         <!--开启属性按需加载-->
    *         <setting name="aggressiveLazyLoading" value="false"/>
    *      </settings>
    *   如果想要快速加载出来的话
    *   fetchType="eager"
    */
    @Test
    public  void test10() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        KeyDao mapper = sqlSession.getMapper(KeyDao.class);
        //4.调用dao方法
        Key keyByIdInSimpleWay = mapper.getKeyByIdInSimpleWay(1);
        //严重性能（也就是浪费）
        System.out.println(keyByIdInSimpleWay.getKeyName());
        //按需加载 需要的时候再去查询 秩序全局开启按需加载策略
        //延迟加载 不着急加载（查询对象）

    }
    //测试collection的分布查询
    //推荐用association连接查询☆☆☆☆☆☆☆☆
    @Test
    public  void test11() throws IOException {
        initSqlSessionFactory();
        //2.获取和数据库的一次对话getConnection()
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.使用sqlSession和数据库交互 获取dao接口的实现
        LockDao mapper = sqlSession.getMapper(LockDao.class);
        //4.调用dao方法
        Lock lockByStep = mapper.getLockByStep(3);

        List<Key> keys = lockByStep.getKeys();

        System.out.println(lockByStep.toString());

    }
}
