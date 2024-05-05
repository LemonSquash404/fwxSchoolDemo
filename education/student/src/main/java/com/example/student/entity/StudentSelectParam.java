package com.example.student.entity;

import lombok.Data;

@Data
public class StudentSelectParam {

    private String id;
    private String name;
    private String sex;
    private String age;

    private long currentPage=1;
    private long pageSize=20;
}
