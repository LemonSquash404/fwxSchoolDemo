package com.example.teacher.service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.teacher.dao.Teacher;
import com.example.teacher.dao.Vo.EditTeacherVo;
import com.example.teacher.dao.Vo.SelectTeacherParam;
import com.example.teacher.service.Mapper.TeacherMapper;
import com.example.teacher.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Autowired
    TeacherMapper teacherMapper;


    @Override
    public List<Teacher> selectAllTeacher() {
        List<Teacher> teachers = teacherMapper.selectList(Wrappers.emptyWrapper());
        teachers.forEach(vo ->{

        });
        return teachers;
    }

    @Override
    public List<Teacher> selectTeacherByPage(SelectTeacherParam selectParam) {
        Page<Teacher> page = new Page<>();
       QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
       queryWrapper.lambda()
               .eq(Teacher::getName,selectParam.getName())
               .eq(Teacher::getSex,selectParam.getSex())
               .eq(Teacher::getAge,selectParam.getAge());
        Page<Teacher> iPage = teacherMapper.selectPage(page, queryWrapper);

        List<Teacher> records = iPage.getRecords();

        //
        records.forEach(vo ->{

        });

        return records;

    }

    @Override
    public int addTeacher(Teacher teacher) {
        int insert = teacherMapper.insert(teacher);
        return insert;
    }

    @Override
    public int editTeacher(EditTeacherVo vo) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,vo);
        int update = teacherMapper.updateById(teacher);

        return update;
    }

    @Override
    public int delTeacher(String ids) {

        int del = teacherMapper.deleteById(ids.split(","));

        return del;

    }
}
