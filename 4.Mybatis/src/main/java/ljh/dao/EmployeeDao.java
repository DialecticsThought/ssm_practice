package ljh.dao;

import ljh.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeDao {
    //查询所有员工
    public List<Employee> getAllEmps();
    //按照员工id查询员工
    public Employee getEmpById(Integer id);
    //自定义一个key用@param
    public Employee getEmpByIdAndEmpName(@Param("id") Integer id,@Param("empname") String empname);

    public Employee getEmpMAPByIdAndEmpName(Map<String,Object> map);
    //更新员工要带员工字段 直接传入对象
    public int updateEmployee(Employee employee);

    public boolean deleteEmployee(Integer id);

    public int insertEmployee(Employee employee);

    public int insertEmployee2(Employee employee);

    // id empName gender email login_account
    // 1,admin,1  ,admin@qq.com,   1
    //把id作为key 把剩下的作为value  一条记录
    public Map<String,Object>getEmpByIdReturnMap(Integer id);

    //返回所有员工 以map形式 多条记录
    //key是value的主键 value是这条记录封装好的对象
    @MapKey("id")//把查询的记录的id作为key封装这个map☆☆☆☆☆☆☆☆
    public Map<Integer,Employee>getAllEmpReturnMap();
}
