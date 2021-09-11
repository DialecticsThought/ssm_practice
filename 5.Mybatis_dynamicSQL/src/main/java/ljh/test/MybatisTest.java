package ljh.test;

import ljh.bean.Teacher;
import ljh.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/*
*
缓存:暂时的存储一些数据 加快系统的查询数据
mybatis的缓存机制：map 能保存查询出的一些数据
一级缓存：线程级别的缓存 也称之为本地缓存 也就是sqlSession级别的缓存（和数据库的一次对话）
二级缓存：全局范围的缓存 除了当前的线程 sqlSession能用外 其他的也能用

一级缓存：线程级别的缓存 也称之为本地缓存 也就是sqlSession级别的缓存（和数据库的一次对话）
只要之前查询过的数据 mybatis就会保存在一个缓存中 下次获取直接从缓存中拿
*
*
*
* */
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
    public void test01(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        //teacher.setId(1);
        //为了模糊查询
        //teacher.setName("%t%");

        //teacher.setBirth(new Date());

        List<Teacher> teacherByCondition = mapper.getTeacherByCondition(teacher);

        List<Teacher> teacherByCondition2 = mapper.getTeacherByCondition(teacher);

        System.out.println(teacherByCondition);

        System.out.println(teacherByCondition2);

        System.out.println(teacherByCondition==teacherByCondition2);
    }

    @Test
    public void test02(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        //teacher.setId(1);
        //为了模糊查询
        //teacher.setName("%t%");

        //teacher.setBirth(new Date());

        List<Teacher> teacherByIdList = mapper.getTeacherByIdList(Arrays.asList(1, 2, 3));

        System.out.println(teacherByIdList);
    }

    @Test
    public void test03(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        teacher.setId(1);
        //为了模糊查询
        teacher.setName("%t%");

        teacher.setBirth(new Date());

        List<Teacher> teacherByConditionChoose = mapper.getTeacherByConditionChoose(teacher);

        System.out.println(teacherByConditionChoose);
    }

    @Test
    public void test04(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        teacher.setId(1);
        //为了模糊查询
        teacher.setName("teacher");

        teacher.setBirth(new Date());

        mapper.updateTeacher(teacher);
        //sqlSessionFactory.openSession(true);
        sqlSession.commit();

        sqlSession.close();
    }
    /*
    * 一级缓存对应的是sqlSession级别的缓存
    *   1.不同的sqlSession使用的是不同的缓存
    *       只有同一个sqlSession期间查询到的数据会保存在这个sqlSession的缓存中  也就是创了新的sqlSession sqlSessionFactory.openSession();
    *       下次使用这个sqlSession查询会从缓存中拿  根据test05
    *   2.同一个方法 不同的参数 由于可能之前没有查过 还是会发出新的sql  根据test06
    *   3.在这个sqlSession期间执行任何一次增删改操作 会把缓存清空 根据test07
    *   4.手动清空缓存 sqlSession.clearCache();
    *
    * 每次查询 先看一级缓存中有没有 如果没有就去发送新的sql 每个sqlsession拥有自己的一级缓存
    *
    * 二级缓存 全局作用域缓存
    * 二级缓存默认不开启 需要手动配置 mybatis-config的settings标签里面cacheEnabled=true
    *                 并在相应的映射文件中 加上<cache></cache>
    *                  相应的bean对象必须实现Serializable
    * 二级缓存 在sqlSession 关闭/提交后 才生效 不能在sqlSession运行期间 ☆☆☆☆☆☆☆☆
    *
    * 一级缓存和二级缓存不会有相同的数据
    *   因为二级缓存：一级缓存关闭后才有
    *       一级缓存：二级缓存中没有数据 才会查看一级缓存 一级缓存没有回去查数据库 并把结果放在一级缓存
    * 任何时候都是先看二级缓存 再看一级缓存 如果大家都没有就去查数据库
    *   2 -> 1 -> database
    * */
    @Test
    public void test05(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        teacher.setId(1);
        //为了模糊查询
        teacher.setName("%t%");

        teacher.setBirth(new Date());

        List<Teacher> teacherByConditionChoose = mapper.getTeacherByConditionChoose(teacher);

        System.out.println(teacherByConditionChoose);

        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        TeacherDao mapper1 = sqlSession1.getMapper(TeacherDao.class);

        List<Teacher> teacherByConditionChoose1 = mapper1.getTeacherByConditionChoose(teacher);

        System.out.println(teacherByConditionChoose1);

        System.out.println(teacherByConditionChoose == teacherByConditionChoose1);

    }

    @Test
    public void test06(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        teacher.setId(1);
        //为了模糊查询
        teacher.setName("%t%");

        teacher.setBirth(new Date());

        List<Teacher> teacherByConditionChoose = mapper.getTeacherByConditionChoose(teacher);

        System.out.println(teacherByConditionChoose);

        Teacher teacher2 = new Teacher();

        teacher2.setId(2);
        //为了模糊查询
        teacher2.setName("%t%");

        teacher2.setBirth(new Date());

        List<Teacher> teacherByConditionChoose1 = mapper.getTeacherByConditionChoose(teacher2);

        System.out.println(teacherByConditionChoose1);
    }


    @Test
    public void test07(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        teacher.setId(1);
        //为了模糊查询
        teacher.setName("%t%");

        teacher.setBirth(new Date());

        List<Teacher> teacherByConditionChoose = mapper.getTeacherByConditionChoose(teacher);

        System.out.println(teacherByConditionChoose);

        //中间还执行了任何一个增删改操作
        Teacher teacher2 = new Teacher();

        teacher2.setId(3);
        //为了模糊查询
        teacher2.setName("teacher3");

        teacher2.setBirth(new Date());

        mapper.updateTeacher(teacher);

        List<Teacher> teacherByConditionChoose2 = mapper.getTeacherByConditionChoose(teacher);

        System.out.println(teacherByConditionChoose == teacherByConditionChoose2);

        sqlSession.commit();

        sqlSession.close();
    }


    @Test
    public void test08(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        teacher.setId(1);
        //为了模糊查询
        teacher.setName("%t%");

        teacher.setBirth(new Date());

        List<Teacher> teacherByConditionChoose = mapper.getTeacherByConditionChoose(teacher);

        System.out.println(teacherByConditionChoose);

        System.out.println("手动清空缓存");

        sqlSession.clearCache();

        List<Teacher> teacherByConditionChoose2 = mapper.getTeacherByConditionChoose(teacher);

        System.out.println(teacherByConditionChoose == teacherByConditionChoose2);

        sqlSession.commit();

        sqlSession.close();
    }


    @Test
    public void test09(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        TeacherDao mapper = sqlSession.getMapper(TeacherDao.class);

        Teacher teacher = new Teacher();

        teacher.setId(1);
        //为了模糊查询
        teacher.setName("%t%");

        teacher.setBirth(new Date());

        List<Teacher> teacherByConditionChoose = mapper.getTeacherByConditionChoose(teacher);
        //必须要关掉才能用二级缓存
        sqlSession.close();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        TeacherDao mapper1 = sqlSession2.getMapper(TeacherDao.class);

        List<Teacher> teacherByConditionChoose1 = mapper1.getTeacherByConditionChoose(teacher);
        //必须要关掉才能用二级缓存
        sqlSession.close();

        System.out.println(teacherByConditionChoose == teacherByConditionChoose1);

    }
}
