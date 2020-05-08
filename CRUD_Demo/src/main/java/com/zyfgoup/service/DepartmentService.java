package com.zyfgoup.service;



import com.zyfgoup.entity.Department;

import java.util.List;


public interface DepartmentService {
    List<Department> selectAllDepartment();

    Department getById(int id);
}
