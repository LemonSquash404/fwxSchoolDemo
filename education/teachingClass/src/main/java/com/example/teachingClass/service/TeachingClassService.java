package com.example.teachingClass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teachingClass.dao.TeachingClass;
import com.example.teachingClass.dao.Vo.EditTeachingClassVo;
import com.example.teachingClass.dao.Vo.SelectTeachingClassParam;
import com.example.teachingClass.dao.Vo.TeachingClassVo;

import java.util.List;

public interface TeachingClassService extends IService<TeachingClass> {

    List<TeachingClass> selectAllTeachingClass();
    List<TeachingClass> selectTeachingClassByPage(SelectTeachingClassParam selectParam);

    int addTeachingClass(TeachingClass teachingClass);
    int editTeachingClass(EditTeachingClassVo vo);
    int delTeachingClass(String ids);

    //查询学生不超过 num 的班级
    List<TeachingClassVo> selectNotFullTeachingClass(Integer grade, Integer stuNum);
}
