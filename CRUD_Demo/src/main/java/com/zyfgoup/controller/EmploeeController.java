package com.zyfgoup.controller;

import com.zyfgoup.dto.EmployeeDTO;
import com.zyfgoup.entity.Department;
import com.zyfgoup.entity.Employee;
import com.zyfgoup.service.DepartmentService;
import com.zyfgoup.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Controller
public class EmploeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    //查询所有员工，返回列表页面
    @GetMapping("/emp")
    public String list(Model model){
       List<EmployeeDTO> employees = employeeService.selectAllEmployee();

//        将结果放在请求中
        model.addAttribute("emps",employees);
        return "emp/list";
    }

    //to员工添加页面
    @GetMapping("/toadd")
    public String toAdd(Model model){
        //查出所有的部门，提供选择
        Collection<Department> departments = departmentService.selectAllDepartment();
        model.addAttribute("departments",departments);
        return "emp/add";
    }

    //员工添加功能，使用post接收
    @PostMapping("/add")
    //用ajax的JSON提交时 需要使用RequestBody  返回JSON数据ResponseBody
    //只是提交表单的话 不需要
    @ResponseBody
    public String add(@RequestBody Employee employee){

        System.out.println(employee);

        //保存员工信息
        employeeService.addEmployee(employee);
        //回到员工列表页面，可以使用redirect或者forward
        return "1";
    }


    //返回JSON  要用ResponseBody
    @GetMapping("/getDep")
    @ResponseBody
    public List<Department> getDep(){
        List<Department> departments = departmentService.selectAllDepartment();
        return departments;
    }

    //to员工修改页面
    @GetMapping("/emp/{id}")
    public String toUpdateEmp(@PathVariable("id") Integer id, Model model){
        //根据id查出来员工
        Employee employee = employeeService.selectEmployeeById(id);
        //将员工信息返回页面
        model.addAttribute("emp",employee);
        //查出所有的部门，提供修改选择
        Collection<Department> departments = departmentService.selectAllDepartment();
        model.addAttribute("departments",departments);

        return "emp/update";
    }

    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee){
        employeeService.updateEmployee(employee);
        //回到员工列表页面
        return "redirect:/emp";
    }

    @GetMapping("/delEmp/{id}")
    public String deleteEmp(@PathVariable("id")Integer id){
        //根据id删除员工
        employeeService.deleteEmployee(id);
        return "redirect:/emp";
    }


}
