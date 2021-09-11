package ljh.dao;

import ljh.bean.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherDao {
    public Teacher getTeacherById(Integer id);
    //传一个对象 有什么属性就用这个属性查出该老师的所有信息
    public List<Teacher> getTeacherByCondition(Teacher teacher);

    public  List<Teacher> getTeacherByIdList(@Param("idList") List<Integer> idList);
    //传一个对象 有什么属性就用这个属性查出该老师的所有信息
    public List<Teacher> getTeacherByConditionChoose(Teacher teacher);

    public int updateTeacher(Teacher teacher);
}
