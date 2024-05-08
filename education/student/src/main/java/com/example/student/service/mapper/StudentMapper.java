package com.example.student.service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student.entity.Student;
import com.example.student.entity.Vo.StudentUpdateVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("SELECT stu.id,stu.grade FROM student stu " +
            "LEFT JOIN student_class cla ON stu.id = cla.student_id AND cla.del_flag = 0 " +
            "WHERE stu.del_flag = 0 and stu.grade = #{grade} and class_id is null")
    List<Student> selectNoClassStudentByGrade(@Param("grade") int grade);

}
