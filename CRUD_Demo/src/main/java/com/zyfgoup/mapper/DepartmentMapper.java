package com.zyfgoup.mapper;

import com.zyfgoup.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface DepartmentMapper {
    List<Department> selectAllDepartment();

    Department getById(int id);
}
