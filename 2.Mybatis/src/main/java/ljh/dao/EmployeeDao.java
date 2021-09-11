package ljh.dao;

import ljh.bean.Employee;

public interface EmployeeDao {
    //按照员工id查询员工
    public Employee getEmpById(Integer id);
    //更新员工要带员工字段 直接传入对象
    public int updateEmployee(Employee employee);

    public boolean deleteEmployee(Integer id);

    public int insertEmployee(Employee employee);
}
