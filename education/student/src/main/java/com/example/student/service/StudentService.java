package com.example.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.student.entity.Student;
import com.example.student.entity.Vo.StudentSelectParam;
import com.example.student.entity.Vo.StudentUpdateVo;

import java.util.List;

public interface StudentService extends IService<Student> {
   List<Student> selectAll();

   List<Student> selectByRedis(StudentSelectParam param);

   List<Student> selectByPage(StudentSelectParam param);

   String add(StudentUpdateVo student);

   String updateNameById(StudentUpdateVo student);

   int delete(String ids);



  void autoDivideClassesForStudent(Integer grade);

   void divideClassesForStudent(Integer studentId,Integer classId);



}
