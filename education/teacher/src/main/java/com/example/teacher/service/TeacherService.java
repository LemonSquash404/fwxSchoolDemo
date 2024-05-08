package com.example.teacher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teacher.dao.Teacher;
import com.example.teacher.dao.Vo.EditTeacherVo;
import com.example.teacher.dao.Vo.SelectTeacherParam;

import java.util.List;

public interface TeacherService extends IService<Teacher> {

    List<Teacher> selectAllTeacher();
    List<Teacher> selectTeacherByPage(SelectTeacherParam selectParam);

    int addTeacher(Teacher teacher);
    int editTeacher(EditTeacherVo param);
    int delTeacher(String ids);

}
