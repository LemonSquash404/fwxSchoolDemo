package com.example.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.student.entity.Student;
import com.example.student.entity.StudentSelectParam;
import com.example.student.entity.StudentUpdateParam;

import java.util.List;

public interface ServerService extends IService<Student> {
   List<Student> see1();

   List<Student> selectByRedis(StudentSelectParam param);

   List<Student> selectByPage(StudentSelectParam param);

   String add1(StudentUpdateParam student);

   String updateNameById(StudentUpdateParam student);

   String delete1(String id);
}
