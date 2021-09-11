package ljh.dao;

import ljh.bean.Employee;
import org.apache.ibatis.annotations.*;

public interface EmployeeDaoAnnotation {

        //按照员工id查询员工
        @Select("select * from t_employee where id = #{id}")
        public Employee getEmpById(Integer id);

        //更新员工要带员工字段 直接传入对象
        @Update("        update t_employee\n" +
                "            set empname =#{empname}, gender=#{gender},email=#{email}\n" +
                "            where id=#{id}")
        public int updateEmployee(Employee employee);

        @Delete("delete from t_employee where id=#{id}")
        public boolean deleteEmployee(Integer id);

        @Insert("insert into t_employee(empname,gender,email) value (#{empname},#{gender},#{email})")
        public int insertEmployee(Employee employee);
}
