package com.example.teachingClass.service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.teachingClass.dao.TeachingClass;
import com.example.teachingClass.dao.Vo.EditTeachingClassVo;
import com.example.teachingClass.dao.Vo.SelectTeachingClassParam;
import com.example.teachingClass.dao.Vo.TeachingClassVo;
import com.example.teachingClass.service.TeachingClassService;
import com.example.teachingClass.service.Mapper.TeachingClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeachingClassServiceImpl extends ServiceImpl<TeachingClassMapper, TeachingClass> implements TeachingClassService {


    @Autowired
    TeachingClassMapper teachingClassMapper;


    @Override
    public List<TeachingClass> selectAllTeachingClass() {
        List<TeachingClass> teachers = teachingClassMapper.selectList(Wrappers.emptyWrapper());
        teachers.forEach(vo ->{

        });
        return teachers;
    }

    @Override
    public List<TeachingClass> selectTeachingClassByPage(SelectTeachingClassParam selectParam) {
        Page<TeachingClass> page = new Page<>();
       QueryWrapper<TeachingClass> queryWrapper = new QueryWrapper<>();
       queryWrapper.lambda()
               .eq(TeachingClass::getClassName,selectParam.getClassName());

        Page<TeachingClass> iPage = teachingClassMapper.selectPage(page, queryWrapper);

        List<TeachingClass> records = iPage.getRecords();

        //
        records.forEach(vo ->{

        });

        return records;

    }

    @Override
    public int addTeachingClass(TeachingClass TeachingClass) {
        int insert = teachingClassMapper.insert(TeachingClass);
        return insert;
    }

    @Override
    public int editTeachingClass(EditTeachingClassVo vo) {
        UpdateWrapper<TeachingClass> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(TeachingClass::getId,vo.getId())
                        .set(TeachingClass::getClassName,vo.getClassName());

        int update = teachingClassMapper.update(updateWrapper);

        return update;
    }

    @Override
    public int delTeachingClass(String ids) {

        int success = 0;

        for (String id : ids.split(",")) {
            int i = teachingClassMapper.deleteById(id);
            success += i;
        }

        return success;

    }

    @Override
    public List<TeachingClassVo> selectNotFullTeachingClass(Integer grade,Integer stuNum) {
        List<TeachingClassVo> teachingClasses = teachingClassMapper.selectNotFullTeachingClass(grade,stuNum);
        return teachingClasses;
    }
}
