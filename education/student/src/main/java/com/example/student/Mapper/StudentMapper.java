package com.example.student.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student.entity.Student;
import com.example.student.entity.StudentUpdateParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select id,name,sex,age from student order by creat_time")
    List<Student> selectStudent();

//    @Select("select * from student limit #{page.current},#{page.size}")
//    List<Student> selectByPage(Page<Student> page);

    @Insert("insert into student (name,sex,age) values(#{student.name},#{student.sex},#{student.age})")
    boolean add1(@Param("student") StudentUpdateParam param);

//    @Update("update 'student' set name = #{student.name} where id = #{student.id}")
//    boolean updateNameById(Student student);

    @Delete("delete  from student where id = #{id}")
    boolean delete1(@Param("id") String id);
}
