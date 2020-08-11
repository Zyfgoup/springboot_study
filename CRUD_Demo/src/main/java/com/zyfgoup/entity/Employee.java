package com.zyfgoup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id;
    private String employeeName;
    private String email;
    private int gender;
    private int departmentId;
    private Date date;




}
