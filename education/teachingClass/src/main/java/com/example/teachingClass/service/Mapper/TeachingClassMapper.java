package com.example.teachingClass.service.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachingClass.dao.TeachingClass;
import com.example.teachingClass.dao.Vo.TeachingClassVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeachingClassMapper extends BaseMapper<TeachingClass> {

    @Select("SELECT * FROM ( SELECT cla.id id, COUNT(scl.student_id)  classStuNum FROM teaching_class cla " +
            "LEFT JOIN student_class  scl on cla.id = scl.class_id and cla.del_flag = '0' " +
            "WHERE cla.del_flag = '0' and cla.grade = '2021' GROUP BY cla.id ) t WHERE t.classStuNum < #{stuNum}")
    List<TeachingClassVo> selectNotFullTeachingClass(@Param("grade") int grade,@Param("stuNum")int stuNum);
}
