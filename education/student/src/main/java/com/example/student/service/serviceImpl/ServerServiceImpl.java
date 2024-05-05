package com.example.student.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.Mapper.StudentMapper;
import com.example.student.entity.Student;
import com.example.student.entity.StudentSelectParam;
import com.example.student.entity.StudentUpdateParam;
import com.example.student.service.ServerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ServerServiceImpl extends ServiceImpl<StudentMapper,Student> implements ServerService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<Student> see1() {
        List<Student> students = studentMapper.selectStudent();
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
        List<Student> students = studentMapper.selectStudent();
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
    public String add1(StudentUpdateParam param) {
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
    public String updateNameById(StudentUpdateParam student) {

        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Student::getId,student.getId())
                .set(Student::getName,student.getName())
                .set(Student::getSex,student.getSex())
                .set(Student::getAge,student.getAge());
        int update = studentMapper.update(updateWrapper);
        if (update>0){
            return "修改成功";
        }else {
            return "修改失败";
        }
    }

    @Override
    public String delete1(String id) {
        boolean flag = studentMapper.delete1(id);
        if (flag){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }
}
