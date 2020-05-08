package com.zyfgoup.service;



import com.zyfgoup.dto.EmployeeDTO;
import com.zyfgoup.entity.Employee;

import java.util.List;



public interface EmployeeService {
    //查询全部员工信息
    List<EmployeeDTO> selectAllEmployee();
    //根据id查询员工信息
    Employee selectEmployeeById(int id);
    //添加一个员工信息
    int addEmployee(Employee employee);
    //修改一个员工信息
    int updateEmployee(Employee employee);
    //根据id删除员工信息
    int deleteEmployee(int id);
}
