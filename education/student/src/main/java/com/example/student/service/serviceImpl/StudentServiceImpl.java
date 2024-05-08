package com.example.student.service.serviceImpl;

import base.ActionResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.api.TeachingClassApi;
import com.example.student.entity.StudentClassManager;
import com.example.student.entity.Vo.TeachingClassVo;
import com.example.student.service.mapper.StudentClassManagerMapper;
import com.example.student.service.mapper.StudentMapper;
import com.example.student.entity.Student;
import com.example.student.entity.Vo.StudentSelectParam;
import com.example.student.entity.Vo.StudentUpdateVo;
import com.example.student.service.StudentService;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    TeachingClassApi teachingClassApi;

    @Autowired
    StudentClassManagerMapper studentClassManagerMapper;


    @Override
    public List<Student> selectAll() {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Student::getCreatTime);
        List<Student> students = studentMapper.selectList(queryWrapper);
        return students;
    }

    @Override
    public List<Student> selectByRedis(StudentSelectParam param) {

        //使用hash表存储学生查询条件，查询后建立set分页取出

        if (redisTemplate.hasKey("student")){
            //从hash中模糊查询
            String key = "";
            if (StringUtils.isNotBlank(param.getName())){
                key+=param.getName()+":";
            }else {
                key+="*"+":";
            }
            if (StringUtils.isNotBlank(param.getAge())){
                key+=param.getAge()+":";
            }else {
                key+="*"+":";
            }
            if (StringUtils.isNotBlank(param.getSex())){
                key+=param.getSex();
            }else {
                key+="*";
            }
            //查询是否之前是否有带这个查询条件查过,如果没有则模糊查询    并加入list用作分页
            if (!redisTemplate.hasKey(key+"_UnClearSelect")){
                Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan("student", ScanOptions.scanOptions().match(key).build());
                //将条件删选后的数据存入zset里
                while (cursor.hasNext()){
                    Map.Entry<Object, Object> next = cursor.next();
                    String stu = JSON.toJSONString(next.getValue());
                    //加入临时list用做分页
                    redisTemplate.opsForList().leftPush(key+"_UnClearSelect",stu);
                }
//                并设置为1分钟过期
                redisTemplate.expire(key+"_UnClearSelect",1, TimeUnit.MINUTES);
            }

            //从zset中分页查询
            long currentPage = param.getCurrentPage();
            long pageSize = param.getPageSize();
            long pageStart = (currentPage - 1) * pageSize ;
            long pageEnd = currentPage * pageSize -1;
            List<Object> studentList = redisTemplate.opsForList().range(key+"_UnClearSelect", pageStart, pageEnd);
            if(ObjectUtils.isNotEmpty(studentList)){
                //JSON转对象
                List<Student> students = new ArrayList<>();
                for (Object o : studentList) {
                    Student student =  JSON.parseObject(JSON.parse(o.toString()).toString(),Student.class);
//
                    students.add(student);
                }
                return students;
            }
        }
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Student::getCreatTime);
        List<Student> students = studentMapper.selectList(queryWrapper);
        //将查询信息放入hash结构中
        for (Student student : students) {
            String key = student.getName()+":"+student.getAge()+":"+student.getSex();
            redisTemplate.opsForHash().put("student",key, JSON.toJSONString(student));
        }

        return students;
    }

    @Override
    public List<Student> selectByPage(StudentSelectParam param) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(param.getName()),Student::getName,param.getName())
                .eq(StringUtils.isNotBlank(param.getAge()),Student::getAge,param.getAge())
                .eq(StringUtils.isNotBlank(param.getSex()),Student::getSex,param.getSex())
                .orderByAsc(Student::getCreatTime);


//        List<Student> students = studentMapper.selectByPage(page);
        Page<Student> page = new Page<>(param.getCurrentPage(),param.getPageSize());
        Page<Student> iPage = studentMapper.selectPage(page, queryWrapper);
        List<Student> records = iPage.getRecords();

        return records;
    }

    @Override
    public String add(StudentUpdateVo param) {
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        Student student = new Student();
        BeanUtils.copyProperties(param,student);
        student.setCreatTime(new Date());
        int insert = studentMapper.insert(student);
//        boolean flag = studentMapper.add1(param);
        if (insert>0){
            return "添加成功";
        }else {
            return "添加失败";
        }

    }

    @Override
    public String updateNameById(StudentUpdateVo vo) {

        Student student = new Student();
        BeanUtils.copyProperties(vo,student);
        student.setEditTime(new Date());
        int update = studentMapper.updateById(student);
        if (update>0){
            return "修改成功";
        }else {
            return "修改失败";
        }
    }

    @Override
    public int delete(String ids) {
        int success = 0;
        for (String id : ids.split(",")) {
            int i = studentMapper.deleteById(id);
            success +=i ;
        }
        return success;

    }

    @Override
    public void autoDivideClassesForStudent(Integer grade) {
        //查询还没有班级的学生
        List<Student> studentList =   studentMapper.selectNoClassStudentByGrade(grade);

       //查询学生没满的班级 5个为满
        int fullNum = 5;
        ActionResult<List<TeachingClassVo>> actionResult = teachingClassApi.selectNotFullTeachingClass(grade, fullNum);

        if (actionResult.getCode().equals(200) && ObjectUtils.isNotEmpty(actionResult.getData())){

            //按顺序分配班级
          List<TeachingClassVo> classVoList = actionResult.getData();


          List<StudentClassManager> managerList = new ArrayList();

            for (Student student : studentList) {

                StudentClassManager manager = new StudentClassManager();
                for (TeachingClassVo classVo : classVoList) {
                    int stuNum = classVo.getClassStuNum();
                    if (stuNum<5){
                        manager.setStudentId(student.getId());
                        manager.setClassId(classVo.getId());
                        manager.setCreatTime(new Date());
                        classVo.setClassStuNum(stuNum + 1);;
                    }
                }
                managerList.add(manager);
            }

            for (StudentClassManager studentClassManager : managerList) {
                studentClassManagerMapper.insert(studentClassManager);
            }
        }else {
            throw new RuntimeException("获取未满班级失败");
        }



    }

    public void divideClassesForStudent(Integer studentId,Integer classId){

        UpdateWrapper<StudentClassManager> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                        .eq(StudentClassManager::getStudentId,studentId)
                        .set(StudentClassManager::getDelTime,new Date());
        //删除原有分班
        studentClassManagerMapper.delete(updateWrapper);
        //建立新分班
        StudentClassManager studentClassManager = new StudentClassManager();
        studentClassManager.setStudentId(studentId);
        studentClassManager.setClassId(classId);
        studentClassManager.setCreatTime(new Date());
        studentClassManagerMapper.insert(studentClassManager);

    }

}
