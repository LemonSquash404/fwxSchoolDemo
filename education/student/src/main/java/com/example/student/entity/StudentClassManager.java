package com.example.student.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("student_class")
@Data
public class StudentClassManager {

    @TableId("id")
    private int id;
    @TableField("student_id")
    private int studentId;
    @TableField("class_id")
    private int classId;
    @TableField("creat_time")
    private Date creatTime;
    @TableField("del_time")
    private Date delTime;
    @TableField("del_flag")
    private int delFlag;


}
